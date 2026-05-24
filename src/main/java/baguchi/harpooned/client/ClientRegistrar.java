package baguchi.harpooned.client;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.client.render.IronHarpoonRenderer;
import baguchi.harpooned.register.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Harpooned.MODID, value = Dist.CLIENT)
public class ClientRegistrar {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.IRON_HARPOON.get(), IronHarpoonRenderer::new);
    }
}
