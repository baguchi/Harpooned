package baguchi.harpooned.mixin.client;

import baguchi.harpooned.item.HarpoonCrossbowItem;
import baguchi.harpooned.register.ModItems;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "isChargedCrossbow", at = @At("HEAD"), cancellable = true)
    private static void isChargedCrossbow(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(ModItems.HARPOON_CROSSBOW)) {
            cir.setReturnValue(HarpoonCrossbowItem.isCharged(stack));
        }
    }
}