package baguchi.harpooned.register;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.entity.Harpoon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister.Entities ENTITIES_REGISTRY = DeferredRegister.createEntities(Harpooned.MODID);


    public static final DeferredHolder<EntityType<?>, EntityType<Harpoon>> IRON_HARPOON = ENTITIES_REGISTRY.registerEntityType("iron_harpoon", Harpoon::new, MobCategory.MISC, (builder) -> builder.sized(0.6F, 0.6F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20));

}