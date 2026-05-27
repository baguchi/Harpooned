package baguchi.harpooned.data.generator.recipe;

import baguchi.harpooned.register.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(PackOutput generator, CompletableFuture<HolderLookup.Provider> p_323846_) {
        super(generator, p_323846_);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.IRON_HARPOON.get(), 4)
                .pattern("B")
                .pattern("S")
                .pattern("N")
                .define('B', Items.IRON_BARS)
                .define('S', Items.STICK)
                .define('N', Items.IRON_NUGGET)
                .unlockedBy("has_item", has(Items.IRON_NUGGET))
                .save(recipeOutput);
    }
}
