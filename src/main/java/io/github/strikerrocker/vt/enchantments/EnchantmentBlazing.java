package io.github.strikerrocker.vt.enchantments;

import io.github.strikerrocker.vt.misc.VTUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;
import java.util.Random;

/**
 * Automatically smelts the drops of harvested blocks
 */
public class EnchantmentBlazing extends VTEnchantmentBase {
    private static Random random = new Random();

    public EnchantmentBlazing() {
        super("blazing", Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, EntityEquipmentSlot.MAINHAND);
        this.setRegistryName("blazing");
        this.setName("blazing");
    }

    @Override
    public void performAction(Entity entity, Event baseEvent) {
        if (entity != null && this.getEnchantmentLevel(((EntityLivingBase) entity).getHeldItemMainhand()) > 0) {
            HarvestDropsEvent event = (HarvestDropsEvent) baseEvent;
            List<ItemStack> drops = event.getDrops();
            List<ItemStack> dropsCopy = VTUtils.copyList(drops);
            drops.clear();
            for (ItemStack drop : dropsCopy)
                if (drop != null) {
                    ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(drop);
                    if (!smeltingResult.isEmpty()) {
                        smeltingResult = smeltingResult.copy();
                        smeltingResult.setCount(smeltingResult.getCount() * drop.getCount());
                        int fortuneLevel = event.getFortuneLevel();
                        if (!(smeltingResult.getItem() instanceof ItemBlock))
                            smeltingResult.setCount(smeltingResult.getCount() * (random.nextInt(fortuneLevel + 1) + 1));
                        drops.add(smeltingResult);
                    } else
                        drops.add(drop);
                }
        }
    }

    @Override
    public int getMinimumEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaximumEnchantability(int enchantmentLevel) {
        return 61;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.SILK_TOUCH;
    }
}
