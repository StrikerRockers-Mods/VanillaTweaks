package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;

public class ModifiedCreativeTab extends Feature {
    /*private boolean commandBlockInRedstone;
    private boolean dragonEggInDecorations;
    private boolean renamedButtons;

    @Override
    public void syncConfig(Configuration config, String category) {
        commandBlockInRedstone = config.get(category, "commandBlockInRedstone", true, "Want command block to appear in redstone tab.").setRequiresMcRestart(true).getBoolean();
        dragonEggInDecorations = config.get(category, "dragonEggInDecorations", true, "Want dragon egg to appear in decorations tab?").setRequiresMcRestart(true).getBoolean();
        renamedButtons = config.get(category, "renamedButtons", true, "Want buttons to be renamed according to their material?").setRequiresMcRestart(true).getBoolean();
    }*/

    @Override
    public void setup() {
        /*if (commandBlockInRedstone)
            Blocks.COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
        if (dragonEggInDecorations)
            Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
        if (renamedButtons) {
            Blocks.STONE_BUTTON.setTranslationKey("buttonStone");
            Blocks.WOODEN_BUTTON.setTranslationKey("buttonWood");
        }*/
    }
}
