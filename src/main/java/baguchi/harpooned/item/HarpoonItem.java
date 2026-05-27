package baguchi.harpooned.item;

import baguchi.harpooned.entity.Harpoon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class HarpoonItem extends ArrowItem {
    public HarpoonItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity owner, @Nullable ItemStack firedFromWeapon) {
        return new Harpoon(level, owner, itemStack.copyWithCount(1), firedFromWeapon);
    }
}
