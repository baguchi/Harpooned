package baguchi.harpooned.data.generator;

import baguchi.harpooned.Harpooned;
import baguchi.harpooned.register.ModItemTags;
import baguchi.harpooned.register.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> provider, ExistingFileHelper exFileHelper) {
        super(packOutput, lookupProvider, provider, Harpooned.MODID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {

        this.tag(ItemTags.CROSSBOW_ENCHANTABLE).add(ModItems.HARPOON_CROSSBOW.asItem());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.HARPOON_CROSSBOW.asItem());
        this.tag(ItemTags.VANISHING_ENCHANTABLE).add(ModItems.HARPOON_CROSSBOW.asItem());
        this.tag(Tags.Items.TOOLS_CROSSBOW).add(ModItems.HARPOON_CROSSBOW.asItem());

        this.tag(ModItemTags.HARPOONS).add(ModItems.IRON_HARPOON.get());
        this.tag(ModItemTags.CAN_SHOOT_HARPOON_CROSSBOW).add(ModItems.IRON_HARPOON.get());

        this.tag(ItemTags.ARROWS).addTag(ModItemTags.HARPOONS);
    }
}
