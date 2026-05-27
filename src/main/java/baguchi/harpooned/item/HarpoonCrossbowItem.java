package baguchi.harpooned.item;

import baguchi.harpooned.register.ModItemTags;
import baguchi.harpooned.register.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class HarpoonCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> HARPOON_ONLY = (itemStack) -> itemStack.is(ModItemTags.CAN_SHOOT_HARPOON_CROSSBOW);

    public HarpoonCrossbowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return HARPOON_ONLY;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return HARPOON_ONLY;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ChargedProjectiles chargedProjectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            this.performShooting(level, player, hand, itemStack, getShootingPower(chargedProjectiles), 1.0F, null);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return super.use(level, player, hand);
        }
    }

    @Override
    public boolean useOnRelease(ItemStack itemStack) {
        return true;
    }

    private static float getShootingPower(ChargedProjectiles projectiles) {
        return projectiles.contains(Items.FIREWORK_ROCKET) ? 1.6F : 3.6F;
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return ModItems.IRON_HARPOON.toStack();
    }
}
