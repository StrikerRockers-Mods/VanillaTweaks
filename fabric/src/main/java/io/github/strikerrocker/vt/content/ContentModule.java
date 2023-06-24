package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.content.blocks.FabricBlocks;
import io.github.strikerrocker.vt.content.items.FabricItems;

public class ContentModule extends FabricModule {
    @Override
    public void addFeatures() {
        registerFeature("blocks", new FabricBlocks());
        registerFeature("items", new FabricItems());
    }
}