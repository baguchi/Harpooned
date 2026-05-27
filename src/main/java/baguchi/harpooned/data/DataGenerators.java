package baguchi.harpooned.data;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.data.generator.EntityTagGenerator;
import baguchi.harpooned.data.generator.ItemTagGenerator;
import baguchi.harpooned.data.generator.ModAdvancementGenerator;
import baguchi.harpooned.data.generator.models.ModItemModels;
import baguchi.harpooned.data.generator.recipe.CraftingGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Harpooned.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(true, new ModItemModels(packOutput, event.getExistingFileHelper()));
        BlockTagsProvider blockModelProvider = new BlockTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper());
        generator.addProvider(true, blockModelProvider);

        generator.addProvider(true, new ItemTagGenerator(packOutput, lookupProvider, blockModelProvider.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(true, new EntityTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper()));
        generator.addProvider(true, new CraftingGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new ModAdvancementGenerator(packOutput, lookupProvider));
      }

}