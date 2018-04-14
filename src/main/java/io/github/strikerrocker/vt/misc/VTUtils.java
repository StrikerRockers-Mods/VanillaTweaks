package io.github.strikerrocker.vt.misc;

import io.github.strikerrocker.vt.vt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
public final class VTUtils
{
    /**
     * Prevents VTUtils from being instantiated
     */
    private VTUtils()
    {
    }

    public static FakePlayer getFakePlayer(World world)
    {
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
    public static <T> T findObject(Object fieldContainer, String... fieldNames)
    {
        Class fieldClass = fieldContainer.getClass();
        while (fieldClass != null)
        {
            try
            {
                Field field = ReflectionHelper.findField(fieldContainer.getClass(), fieldNames);
                return (T) field.get(fieldContainer);
            } catch (Exception exception)
            {
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
    public static <T> List<T> copyList(List<T> list)
    {
        try
        {
            Constructor constructor = list.getClass().getConstructor(Collection.class);
            return (List<T>) constructor.newInstance(list);
        } catch (Exception exception)
        {
            return new ArrayList<>(list);
        }
    }

    public static MethodHandle findFieldGetter(Class c, String... names)
    {
        try
        {
            return MethodHandles.lookup().unreflectGetter(ReflectionHelper.findField(c, names));
        } catch (IllegalAccessException e)
        {
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
    public static void registerDispenserBehavior(Item item, BehaviorDefaultDispenseItem dispenserBehavior)
    {
        String localizedName = I18n.translateToLocal(item.getUnlocalizedName() + ".name");

        if (dispenserBehavior.getClass() != BehaviorDefaultDispenseItem.class)
            vt.logInfo("Registering dispenser behavior for " + localizedName);
        else
            vt.logInfo("Registering default dispenser behavior for " + localizedName);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, dispenserBehavior);
    }
}