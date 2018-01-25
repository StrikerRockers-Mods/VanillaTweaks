package com.strikerrocker.vt.compat.baubles;

import baubles.api.BaubleType;
import com.strikerrocker.vt.items.ItemBase;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.strikerrocker.vt.vt.proxy;

public class BinocularBauble extends Item implements IBauble{
    public BinocularBauble() {
        this.setUnlocalizedName("binocular_bauble");
        this.setRegistryName("binocular_bauble");
        this.setMaxStackSize(1);
    }

    public void registerItemModel() {
        proxy.registerItemRenderer(this, 0, "binocular_bauble");
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.HEAD;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }
}
