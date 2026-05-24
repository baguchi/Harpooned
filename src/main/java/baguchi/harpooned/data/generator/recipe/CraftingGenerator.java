package baguchi.harpooned.data.generator.recipe;

import baguchi.harpooned.register.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(HolderLookup.Provider generator, RecipeOutput completableFuture) {
        super(generator, completableFuture);
    }

    @Override
    protected void buildRecipes() {
        HolderLookup<Item> lookup = this.registries.lookupOrThrow(Registries.ITEM);
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.COMBAT, ModItems.IRON_HARPOON.get(), 4)
                .pattern("B")
                .pattern("S")
                .pattern("N")
                .define('B', Items.IRON_BARS)
                .define('S', Items.STICK)
                .define('N', Items.IRON_NUGGET)
                .unlockedBy("has_item", has(Items.IRON_NUGGET))
                .save(this.output);
    }
}
