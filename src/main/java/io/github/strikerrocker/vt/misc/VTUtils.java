package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.VT;
import io.github.strikerrocker.vt.VTModInfo;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.DyeUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Contains some utility functions used by Vanilla Tweaks
 */
@SuppressWarnings("unchecked")
public class VTUtils {
    /**
     * Prevents VTUtils from being instantiated
     */
    private VTUtils() {
    }

    public static FakePlayer getFakePlayer(World world) {
        if (world instanceof WorldServer)
            return FakePlayerFactory.getMinecraft((WorldServer) world);
        return null;
    }


    /**
     * Finds an object by the specified name(s) in the specified object, and returns it
     *
     * @param fieldContainer The object to find the object in
     * @param fieldNames     A list of all possible names for the object
     * @param <T>            The data type of the object to return
     * @return An object of the specified type with the first possible of the passed names
     */
    public static <T> T findObject(Object fieldContainer, String... fieldNames) {
        Class fieldClass = fieldContainer.getClass();
        while (fieldClass != null) {
            try {
                Field field = ReflectionHelper.findField(fieldContainer.getClass(), fieldNames);
                return (T) field.get(fieldContainer);
            } catch (Exception exception) {
                fieldClass = fieldClass.getSuperclass();
            }
        }
        return null;
    }

    /**
     * Copies a list and returns the copy
     *
     * @param list The list to copy
     * @param <T>  The type of the list
     * @return A copy of the given list
     */
    public static <T> List<T> copyList(List<T> list) {
        try {
            Constructor constructor = list.getClass().getConstructor(Collection.class);
            return (List<T>) constructor.newInstance(list);
        } catch (Exception exception) {
            return new ArrayList<>(list);
        }
    }

    public static MethodHandle findFieldGetter(Class c, String... names) {
        try {
            return MethodHandles.lookup().unreflectGetter(ReflectionHelper.findField(c, names));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registers a dispenser behavior for an item with the game
     *
     * @param item              The item dispensed by the dispenser
     * @param dispenserBehavior The dispenser behavior carried out for the item
     */
    public static void registerDispenserBehavior(Item item, BehaviorDefaultDispenseItem dispenserBehavior) {
        String localizedName = I18n.translateToLocal(item.getUnlocalizedName() + ".name");

        if (dispenserBehavior.getClass() != BehaviorDefaultDispenseItem.class)
            VT.logInfo("Registering dispenser behavior for " + localizedName);
        else
            VT.logInfo("Registering default dispenser behavior for " + localizedName);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, dispenserBehavior);
    }

    public static TextFormatting getColorTextFromStack(ItemStack stack) {
        {
            if (DyeUtils.isDye(stack)) {
                if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 0))) {
                    return TextFormatting.BLACK;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 1))) {
                    return TextFormatting.DARK_RED;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 2))) {
                    return TextFormatting.DARK_GREEN;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 3))) {
                    return TextFormatting.GOLD;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 4))) {
                    return TextFormatting.DARK_BLUE;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 5))) {
                    return TextFormatting.DARK_PURPLE;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 6))) {
                    return TextFormatting.DARK_AQUA;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 7))) {
                    return TextFormatting.GRAY;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 8))) {
                    return TextFormatting.DARK_GRAY;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 9))) {
                    return TextFormatting.LIGHT_PURPLE;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 10))) {
                    return TextFormatting.GREEN;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 11))) {
                    return TextFormatting.YELLOW;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 12))) {
                    return TextFormatting.BLUE;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 13))) {
                    return TextFormatting.AQUA;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 14))) {
                    return TextFormatting.GOLD;
                } else if (stack.isItemEqual(new ItemStack(Items.DYE, 1, 15))) {
                    return TextFormatting.WHITE;
                }
            }
        }
        return TextFormatting.WHITE;
    }

    private static int ind = 0;

    public static EntityEntry createEntityEntry(String name, Class clazz, int range, int update, boolean VelocityUpdate) {
        return EntityEntryBuilder.create().entity(clazz).id(new ResourceLocation(VTModInfo.MOD_ID, name), ind++)
                .name(name).tracker(range, update, VelocityUpdate).build();
    }
}