package io.github.strikerrocker.vt.content.items;

import io.github.strikerrocker.vt.VTModInfo;
import io.github.strikerrocker.vt.compat.CuriosCompat;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class BinocularItem extends ArmorItem {
    public BinocularItem() {
        super(new BasicArmorMaterial(VTModInfo.MODID + ":binoculars", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.fromItems(net.minecraft.item.Items.IRON_INGOT))
                , EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(1).group(ItemGroup.TOOLS));
        setRegistryName("binoculars");
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (ModList.get().isLoaded("curios")) {
            return CuriosCompat.initCapabilities();
        }
        return super.initCapabilities(stack, nbt);
    }
}
