package io.github.strikerrocker.vt.tweaks;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.init.Items;
import net.minecraftforge.common.config.Configuration;

public class StackSizes extends Feature {

    private int bedStackSize;
    private int totemOfUndyingStackSize;
    private int boatStackSize;
    private int cakeStackSize;
    private int enderPearlStackSize;

    @Override
    public void syncConfig(Configuration config, String category) {
        String stackCategory = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "Tweaks" + Configuration.CATEGORY_SPLITTER + "Stack Size";
        bedStackSize = config.get(stackCategory, "bedStackSize", 4, "Stack size for bed", 1, Integer.MAX_VALUE).getInt();
        totemOfUndyingStackSize = config.get(stackCategory, "totemOfUndyingStackSize", 4, "Stack size for Totem of Undying", 1, Integer.MAX_VALUE).getInt();
        boatStackSize = config.get(stackCategory, "boatStackSize", 4, "Stack size for boat", 1, Integer.MAX_VALUE).getInt();
        cakeStackSize = config.get(stackCategory, "cakeStackSize", 4, "Stack size for cake", 1, Integer.MAX_VALUE).getInt();
        enderPearlStackSize = config.get(stackCategory, "enderPearlStackSize", 64, "Stack size for ender pearls", 1, Integer.MAX_VALUE).getInt();
    }

    @Override
    public void preInit() {
        Items.BED.setMaxStackSize(bedStackSize);
        Items.TOTEM_OF_UNDYING.setMaxStackSize(totemOfUndyingStackSize);
        Items.BOAT.setMaxStackSize(boatStackSize);
        Items.ACACIA_BOAT.setMaxStackSize(boatStackSize);
        Items.BIRCH_BOAT.setMaxStackSize(boatStackSize);
        Items.DARK_OAK_BOAT.setMaxStackSize(boatStackSize);
        Items.JUNGLE_BOAT.setMaxStackSize(boatStackSize);
        Items.SPRUCE_BOAT.setMaxStackSize(boatStackSize);
        Items.CAKE.setMaxStackSize(cakeStackSize);
        Items.ENDER_PEARL.setMaxStackSize(enderPearlStackSize);
    }
}
