package baguchi.harpooned.mixin;

import baguchi.harpooned.item.HarpoonCrossbowItem;
import baguchi.harpooned.register.ModItemTags;
import baguchi.harpooned.register.ModItems;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends ProjectileWeaponItem {
    public CrossbowItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "getChargeDuration", at = @At("HEAD"), cancellable = true)
    private static void getChargeDuration(ItemStack stack, LivingEntity shooter, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem() instanceof HarpoonCrossbowItem) {
            float f = EnchantmentHelper.modifyCrossbowChargingTime(stack, shooter, 1.25F * 2);
            cir.setReturnValue(Mth.floor(f * 20.0F));
        }
    }

    @Inject(method = "getShootingPower", at = @At("RETURN"))
    private static void getShootingPower(ChargedProjectiles projectiles, CallbackInfoReturnable<Float> cir) {
        if (!(projectiles.contains(ModItems.IRON_HARPOON.asItem()))) {
            cir.setReturnValue(cir.getReturnValue() - 2);
        }
    }

    @Inject(method = "getDurabilityUse", at = @At("RETURN"), cancellable = true)
    protected void getDurabilityUse(ItemStack projectile, CallbackInfoReturnable<Integer> cir) {

        if (!(this.asItem() instanceof HarpoonCrossbowItem) && projectile.is(ModItemTags.HARPOONS)) {
            cir.setReturnValue(3);
        }
    }

}