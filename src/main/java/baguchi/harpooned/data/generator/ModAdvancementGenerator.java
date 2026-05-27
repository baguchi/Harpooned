package baguchi.harpooned.data.generator;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.register.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementGenerator extends AdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output     the target directory of the data generator
     * @param registries a future of a lookup for registries and their objects
     */
    public ModAdvancementGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new ModAdvancements()));
    }


    public static class ModAdvancements implements AdvancementSubProvider {

        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
            HolderLookup.RegistryLookup<Item> items = provider.lookupOrThrow(Registries.ITEM);
            HolderLookup.RegistryLookup<Block> blocks = provider.lookupOrThrow(Registries.BLOCK);

            AdvancementHolder shotHarpoon = Advancement.Builder.advancement()
                    .parent(ResourceLocation.withDefaultNamespace("adventure/ol_betsy"))
                    .display(
                            ModItems.HARPOON_CROSSBOW.asItem(),
                            Component.translatable("advancements.adventure.shoot_harpoon.title"),
                            Component.translatable("advancements.adventure.shoot_harpoon.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("shot_crossbow", ShotCrossbowTrigger.TriggerInstance.shotCrossbow(ModItems.HARPOON_CROSSBOW))
                    .save(consumer, Harpooned.MODID +":shoot_harpoon");
        }
    }
}
