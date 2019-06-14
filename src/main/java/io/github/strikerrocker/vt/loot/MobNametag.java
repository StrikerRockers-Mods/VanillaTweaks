package io.github.strikerrocker.vt.loot;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobNametag extends Feature {
    private boolean namedMobsDropNameTag;

    @Override
    public void syncConfig(Configuration config, String module) {
        namedMobsDropNameTag = config.get(module, "namedMobsDropNameTag", true, "Does a nametag drop when named mob dies?").getBoolean();
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        World world = entity.world;
        if (!world.isRemote && world.getGameRules().getBoolean("doMobLoot") && event.getSource().damageType != null && namedMobsDropNameTag && !entity.getCustomNameTag().equals("")) {
            ItemStack nameTag = new ItemStack(Items.NAME_TAG);
            nameTag.setStackDisplayName(entity.getCustomNameTag());
            entity.entityDropItem(nameTag, 0);
            entity.setCustomNameTag("");
        }
    }
}