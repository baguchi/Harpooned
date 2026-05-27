package baguchi.harpooned.data.generator;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.register.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends EntityTypeTagsProvider {
    public EntityTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, Harpooned.MODID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider p_255894_) {
        this.tag(EntityTypeTags.ARROWS).add(ModEntities.IRON_HARPOON.get());
    }
}
