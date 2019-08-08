package io.github.strikerrocker.vt.compat.baubles;

import net.minecraft.item.Item;

public class ItemBaubleBino extends Item /*implements IBauble */ {
    ItemBaubleBino() {
        super(new Item.Properties().maxStackSize(1));
        this.setRegistryName("binocular_bauble");
    }
/*
    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return null;
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
        return false;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }*/
}
