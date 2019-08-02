package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.Module;
import io.github.strikerrocker.vt.content.blocks.Blocks;
import io.github.strikerrocker.vt.content.items.Items;

public class ContentModule extends Module {
    public ContentModule() {
        super("Content", "New items, blocks VanillaTweaks adds.", true);
    }

    @Override
    public void addFeatures() {
        registerFeature(new Items());
        registerFeature(new Blocks());
    }
}
