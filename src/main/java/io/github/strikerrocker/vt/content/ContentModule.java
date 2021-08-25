package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.blocks.BlockInit;
import io.github.strikerrocker.vt.content.items.ItemInit;
import net.minecraftforge.common.ForgeConfigSpec;

public class ContentModule extends Module {
    public ContentModule(ForgeConfigSpec.Builder builder) {
        super("content", "New items, blocks VanillaTweaks adds.", builder);
    }

    @Override
    public void addFeatures() {
        registerFeature("items", new ItemInit());
        registerFeature("blocks", new BlockInit());
    }
}