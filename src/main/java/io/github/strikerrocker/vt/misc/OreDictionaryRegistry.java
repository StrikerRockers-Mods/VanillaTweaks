package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.items.VTItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistry
{
    public static void init()
    {
        for (int i = 0; i < 16; i++)
        {
            OreDictionary.registerOre("wool", new ItemStack(Blocks.WOOL, 1, i));
        }
        OreDictionary.registerOre("egg", VTItems.fried_egg);
        OreDictionary.registerOre("ingredientEgg", VTItems.fried_egg);
    }
}
