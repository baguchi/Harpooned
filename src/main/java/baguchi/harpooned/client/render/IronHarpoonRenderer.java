package baguchi.harpooned.client.render;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.entity.Harpoon;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class IronHarpoonRenderer extends ArrowRenderer<Harpoon, ArrowRenderState> {
    public static final Identifier LOCATION = Identifier.fromNamespaceAndPath(Harpooned.MODID, "textures/entity/iron_harpoon.png");

    public IronHarpoonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState state) {
        return LOCATION;
    }

    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
