package baguchi.harpooned.client.render;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.entity.Harpoon;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class IronHarpoonRenderer extends ArrowRenderer<Harpoon> {
    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Harpooned.MODID, "textures/entity/iron_harpoon.png");

    public IronHarpoonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Harpoon state) {
        return LOCATION;
    }
}
