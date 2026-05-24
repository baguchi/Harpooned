package baguchi.harpooned.data;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.data.generator.EntityTagGenerator;
import baguchi.harpooned.data.generator.ItemTagGenerator;
import baguchi.harpooned.data.generator.ModModelData;
import baguchi.harpooned.data.generator.recipe.CraftingGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Harpooned.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(true, new ModModelData(packOutput));

        generator.addProvider(true, new ItemTagGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new EntityTagGenerator(packOutput, lookupProvider));
        generator.addProvider(true, new Runner(packOutput, lookupProvider));
      }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
            return new CraftingGenerator(lookupProvider, output);
        }

        @Override
        public String getName() {
            return Harpooned.MODID + "recipes";
        }
    }
}