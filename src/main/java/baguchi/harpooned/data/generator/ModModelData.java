package baguchi.harpooned.data.generator;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.data.generator.models.ModBlockModels;
import baguchi.harpooned.data.generator.models.ModItemModels;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelDispatcher;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModModelData extends ModelProvider {
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    public ModModelData(PackOutput packOutput) {
        super(packOutput, Harpooned.MODID);
        this.blockStatePathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemInfoPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    public CompletableFuture<?> run(CachedOutput cache) {
        ItemInfoCollector itemModels = new ItemInfoCollector(this::getKnownItems);
        BlockStateGeneratorCollector blockStateGenerators = new BlockStateGeneratorCollector(this::getKnownBlocks);
        SimpleModelCollector simpleModels = new SimpleModelCollector();
        this.registerModels(new ModBlockModels(blockStateGenerators, itemModels, simpleModels), new ModItemModels(itemModels, simpleModels));
        blockStateGenerators.validate();
        itemModels.finalizeAndValidate();
        return CompletableFuture.allOf(blockStateGenerators.save(cache, this.blockStatePathProvider), simpleModels.save(cache, this.modelPathProvider), itemModels.save(cache, this.itemInfoPathProvider));
    }

    private static class BlockStateGeneratorCollector implements Consumer<BlockModelDefinitionGenerator> {
        private final Map<Block, BlockModelDefinitionGenerator> generators;
        private final Supplier<Stream<? extends Holder<Block>>> knownBlocks;

        public BlockStateGeneratorCollector(Supplier<Stream<? extends Holder<Block>>> knownBlocks) {
            this.generators = new HashMap();
            this.knownBlocks = knownBlocks;
        }

        public void accept(BlockModelDefinitionGenerator generator) {
            Block block = generator.block();
            BlockModelDefinitionGenerator prev = (BlockModelDefinitionGenerator)this.generators.put(block, generator);
            if (prev != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + String.valueOf(block));
            }
        }

        public void validate() {
            Stream<? extends Holder<Block>> holders = (Stream)this.knownBlocks.get();
            List<Identifier> missingDefinitions = holders.filter((e) -> !this.generators.containsKey(e.value())).map((e) -> ((ResourceKey)e.unwrapKey().orElseThrow()).identifier()).toList();
            if (!missingDefinitions.isEmpty()) {
                throw new IllegalStateException("Missing blockstate definitions for: " + String.valueOf(missingDefinitions));
            }
        }

        public CompletableFuture<?> save(CachedOutput cache, PackOutput.PathProvider pathProvider) {
            Map<Block, BlockStateModelDispatcher> definitions = Maps.transformValues(this.generators, BlockModelDefinitionGenerator::create);
            Function<Block, Path> pathGetter = (block) -> pathProvider.json(block.builtInRegistryHolder().key().identifier());
            return DataProvider.saveAll(cache, BlockStateModelDispatcher.CODEC, pathGetter, definitions);
        }
    }

    private static class ItemInfoCollector implements ItemModelOutput {
        private final Map<Item, ClientItem> itemInfos;
        private final Map<Item, Item> copies;
        private final Supplier<Stream<? extends Holder<Item>>> knownItems;
        private final Map<Identifier, ClientItem> idItemInfos;

        public ItemInfoCollector(Supplier<Stream<? extends Holder<Item>>> knownItems) {
            this.itemInfos = new HashMap();
            this.copies = new HashMap();
            this.idItemInfos = new HashMap();
            this.knownItems = knownItems;
        }

        /** @deprecated */
        @Deprecated
        public ItemInfoCollector() {
            DefaultedRegistry<Item> var10001 = BuiltInRegistries.ITEM;
            Objects.requireNonNull(var10001);
            this(var10001::listElements);
        }

        public void accept(Item item, ItemModel.Unbaked model, ClientItem.Properties properties) {
            this.register(item, new ClientItem(model, properties));
        }

        public void register(Item item, ClientItem itemInfo) {
            ClientItem prev = this.itemInfos.put(item, itemInfo);
            if (prev != null) {
                throw new IllegalStateException("Duplicate item model definition for " + item);
            }
        }

        public void register(Identifier identifier, ClientItem clientItem) {
            ClientItem existing = this.idItemInfos.putIfAbsent(identifier, clientItem);
            if (existing != null) {
                throw new IllegalStateException("Duplicate item model definition for " + identifier);
            }
        }

        public void copy(Item donor, Item acceptor) {
            this.copies.put(acceptor, donor);
        }

        public void finalizeAndValidate() {
            this.knownItems.get().map(Holder::value).forEach((item) -> {
                if (!this.copies.containsKey(item) && item instanceof BlockItem blockItem) {
                    if (!this.itemInfos.containsKey(blockItem)) {
                        Identifier targetModel = ModelLocationUtils.getModelLocation(blockItem.getBlock());
                        this.accept(blockItem, ItemModelUtils.plainModel(targetModel));
                    }
                }

            });
            this.copies.forEach((acceptor, donor) -> {
                ClientItem donorInfo = this.itemInfos.get(donor);
                if (donorInfo == null) {
                    String var10002 = String.valueOf(donor);
                    throw new IllegalStateException("Missing donor: " + var10002 + " -> " + acceptor);
                } else {
                    this.register(acceptor, donorInfo);
                }
            });
            List<Identifier> missingDefinitions = this.knownItems.get().filter((e) -> !this.itemInfos.containsKey(e.value())).map((e) -> ((ResourceKey)e.unwrapKey().orElseThrow()).identifier()).toList();
            if (!missingDefinitions.isEmpty()) {
                throw new IllegalStateException("Missing item model definitions for: " + missingDefinitions);
            }
        }

        public CompletableFuture<?> save(CachedOutput cache, PackOutput.PathProvider pathProvider) {
            CompletableFuture[] var10000 = new CompletableFuture[]{DataProvider.saveAll(cache, ClientItem.CODEC, (item) -> pathProvider.json(item.builtInRegistryHolder().key().identifier()), this.itemInfos), null};
            Codec var10004 = ClientItem.CODEC;
            Objects.requireNonNull(pathProvider);
            var10000[1] = DataProvider.saveAll(cache, var10004, pathProvider::json, this.idItemInfos);
            return CompletableFuture.allOf(var10000);
        }
    }

    private static class SimpleModelCollector implements BiConsumer<Identifier, ModelInstance> {
        private final Map<Identifier, ModelInstance> models = new HashMap();

        private SimpleModelCollector() {
        }

        public void accept(Identifier id, ModelInstance contents) {
            Supplier<JsonElement> prev = this.models.put(id, contents);
            if (prev != null) {
                throw new IllegalStateException("Duplicate model definition for " + id);
            }
        }

        public CompletableFuture<?> save(CachedOutput cache, PackOutput.PathProvider pathProvider) {
            Objects.requireNonNull(pathProvider);
            return DataProvider.saveAll(cache, Supplier::get, pathProvider::json, this.models);
        }
    }
}
