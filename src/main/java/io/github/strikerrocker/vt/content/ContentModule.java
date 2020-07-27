package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.blocks.Blocks;
import io.github.strikerrocker.vt.content.items.Items;
import net.minecraftforge.common.ForgeConfigSpec;

public class ContentModule extends Module {
    public ContentModule(ForgeConfigSpec.Builder builder) {
        super("content", "New items, blocks VanillaTweaks adds.", true, builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("items", new Items());
        registerFeature("blocks", new Blocks());
    }
}