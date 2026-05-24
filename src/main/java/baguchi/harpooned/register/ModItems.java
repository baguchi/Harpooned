package baguchi.harpooned.register;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.item.HarpoonCrossbowItem;
import baguchi.harpooned.item.HarpoonItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Harpooned.MODID);

    public static final DeferredItem<HarpoonCrossbowItem> HARPOON_CROSSBOW = ITEMS.registerItem("harpoon_crossbow", (properties) -> new HarpoonCrossbowItem(properties.durability(532).rarity(Rarity.UNCOMMON).component(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).enchantable(1).stacksTo(1)));
    public static final DeferredItem<HarpoonItem> IRON_HARPOON = ITEMS.registerItem("iron_harpoon", (properties) -> new HarpoonItem(properties));

}
