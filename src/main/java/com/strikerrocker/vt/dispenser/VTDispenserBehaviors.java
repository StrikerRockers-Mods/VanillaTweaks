package com.strikerrocker.vt.dispenser;

import com.strikerrocker.vt.handlers.VTConfigHandler;
import com.strikerrocker.vt.items.VTItems;
import com.strikerrocker.vt.vt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.util.text.translation.I18n;

public class VTDispenserBehaviors {
    /**
     * Registers the dispenser behaviors for Vanilla Tweaks
     */
    @SuppressWarnings("unchecked")
    public static void registerDispenserBehaviors() {
        Iterable<Item> items = Item.REGISTRY;
        for (Item item : items) {
            /***Falling Blocks
             if (item instanceof ItemBlock) {
             Block block = ((ItemBlock) item).block;
             List<Material> fallingMaterials = Arrays.asList(Material.SAND, Material.GROUND, Material.CLAY, Material.SNOW, Material.CRAFTED_SNOW);
             if ((block instanceof BlockFalling || VTConfigHandler.additionalFallingBlocks.contains(block)) && fallingMaterials.contains(block.getDefaultState().getMaterial()))
             registerDispenserBehavior(item, new BehaviorDispenseBlockFalling());
             }*/
            //Flint And Steel (default behavior)
            if (item instanceof ItemFlintAndSteel && !VTConfigHandler.enableFlintAndSteelDispenserBehavior)
                registerDispenserBehavior(item, new BehaviorDefaultDispenseItem());
        }
        registerDispenserBehavior(VTItems.dynamite, new BehaviorDispenseDynamite());
    }


    /**
     * Registers a dispenser behavior for an item with the game
     *
     * @param item              The item dispensed by the dispenser
     * @param dispenserBehavior The dispenser behavior carried out for the item
     */
    @SuppressWarnings("deprecation")
    private static void registerDispenserBehavior(Item item, BehaviorDefaultDispenseItem dispenserBehavior) {
        String localizedName = I18n.translateToLocal(item.getUnlocalizedName() + ".name");
        if (dispenserBehavior.getClass() != BehaviorDefaultDispenseItem.class)
            vt.logInfo("Registering dispenser behavior for " + localizedName);
        else
            vt.logInfo("Registering default dispenser behavior for " + localizedName);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, dispenserBehavior);
    }
}
