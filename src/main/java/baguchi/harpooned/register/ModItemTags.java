package baguchi.harpooned.register;

import baguchi.harpooned.Harpooned;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> HARPOONS = bind("harpoons");
    public static final TagKey<Item> CAN_SHOOT_HARPOON_CROSSBOW = bind("can_shoot_harpoon_crossbow");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Harpooned.MODID, name));
    }
}
