package baguchi.harpooned.entity;

import baguchi.harpooned.register.ModEntities;
import baguchi.harpooned.register.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Harpoon extends AbstractArrow {
    public Harpoon(EntityType<? extends Harpoon> type, Level level) {
        super(type, level);
        this.setBaseDamage(4.0F);
    }

    public Harpoon(Level level, LivingEntity owner, ItemStack harpoonItem) {
        super(ModEntities.IRON_HARPOON.get(), owner, level, harpoonItem, null);
        this.setBaseDamage(4.0F);
   }

    public Harpoon(Level level, double x, double y, double z, ItemStack tridentItem) {
        super(ModEntities.IRON_HARPOON.get(), x, y, z, level, tridentItem, tridentItem);
        this.setBaseDamage(4.0F);
    }


    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ModItems.IRON_HARPOON.toStack();
    }

    @Override
    protected float getWaterInertia() {
        return 0.98F;
    }

    @Override
    protected double getDefaultGravity() {
        if(!this.isInWater()){
            return 0.08;
        }

        return 0.05;
    }
}
