package baguchi.harpooned.item;

import baguchi.harpooned.register.ModItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public class HarpoonCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> HARPOON_ONLY = (itemStack) -> itemStack.is(ModItemTags.CAN_SHOOT_HARPOON_CROSSBOW);

    public HarpoonCrossbowItem(Item.Properties properties) {
        super(properties);
    }

    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return HARPOON_ONLY;
    }

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return HARPOON_ONLY;
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ChargedProjectiles chargedProjectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            this.performShooting(level, player, hand, itemStack, getShootingPower(chargedProjectiles), 1.0F, null);
            return InteractionResult.CONSUME;
        } else {
            return super.use(level, player, hand);
        }
    }

    private static float getShootingPower(ChargedProjectiles projectiles) {
        return projectiles.contains(Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    public static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack heldItem) {
        List<ItemStack> drawn = draw(heldItem, shooter.getProjectile(heldItem), shooter);
        if (!drawn.isEmpty()) {
            heldItem.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.ofNonEmpty(drawn));
            return true;
        } else {
            return false;
        }
    }
}
