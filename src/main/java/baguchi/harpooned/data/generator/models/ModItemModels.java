package baguchi.harpooned.data.generator.models;

import baguchi.harpooned.data.generator.ModModelData;
import baguchi.harpooned.register.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.CrossbowPull;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.BiConsumer;

public class ModItemModels extends ItemModelGenerators {
    public ModItemModels(ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        this.generateFlatItem(ModItems.IRON_HARPOON.asItem(), ModelTemplates.FLAT_ITEM);
        this.generateHarpoonCrossbow(ModItems.HARPOON_CROSSBOW.asItem());
    }

    public void generateHarpoonCrossbow(Item item) {
        ItemModel.Unbaked crossbowModel = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_standby", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling0 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_0", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling1 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_1", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked pulling2 = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_pulling_2", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked loadedArrow = ItemModelUtils.plainModel(this.createFlatItemModel(item, "_charged", ModelTemplates.CROSSBOW));
        this.itemModelOutput.accept(item, ItemModelUtils.select(new Charge(), ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), ItemModelUtils.rangeSelect(new CrossbowPull(), pulling0, ItemModelUtils.override(pulling1, 0.58F), ItemModelUtils.override(pulling2, 1.0F)), crossbowModel), ItemModelUtils.when(CrossbowItem.ChargeType.ARROW, loadedArrow)));
    }
}
