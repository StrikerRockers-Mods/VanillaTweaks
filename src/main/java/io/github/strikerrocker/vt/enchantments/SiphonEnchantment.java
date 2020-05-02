package io.github.strikerrocker.vt.enchantments;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class SiphonEnchantment extends Enchantment {
    SiphonEnchantment(String name) {
        super(Rarity.UNCOMMON, EnchantmentType.DIGGER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
        this.setRegistryName(name);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 61;
    }

    @Override
    public int getMaxLevel() {
        return EnchantmentFeature.enableSiphon.get() ? 1 : 0;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ToolItem && EnchantmentFeature.enableSiphon.get();
    }

    @Override
    public boolean isTreasureEnchantment() {
        return EnchantmentFeature.enableSiphon.get();
    }

    private static class SiphonModifier extends LootModifier {
        public SiphonModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            Entity e = context.get(LootParameters.THIS_ENTITY);
            if (e instanceof PlayerEntity)
                generatedLoot.removeIf(((PlayerEntity) e)::addItemStackToInventory);
            return generatedLoot;
        }
    }

    @Override
    public boolean isAllowedOnBooks() {
        return EnchantmentFeature.enableSiphon.get();
    }

    public static class Serializer extends GlobalLootModifierSerializer<SiphonEnchantment.SiphonModifier> {
        @Override
        public SiphonEnchantment.SiphonModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new SiphonEnchantment.SiphonModifier(conditionsIn);
        }
    }
}
