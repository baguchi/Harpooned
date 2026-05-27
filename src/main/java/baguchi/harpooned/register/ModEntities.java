package baguchi.harpooned.register;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.entity.Harpoon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Harpooned.MODID);


    public static final DeferredHolder<EntityType<?>, EntityType<Harpoon>> IRON_HARPOON = ENTITIES_REGISTRY.register("iron_harpoon", () -> EntityType.Builder.<Harpoon>of(Harpoon::new, MobCategory.MISC).sized(0.6F, 0.6F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20).build("harpooned:iron_harpoon"));

}