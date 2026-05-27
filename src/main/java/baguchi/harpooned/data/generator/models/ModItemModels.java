package baguchi.harpooned.data.generator.models;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.register.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, Harpooned.MODID, fileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ModItems.IRON_HARPOON.asItem());
    }
}
