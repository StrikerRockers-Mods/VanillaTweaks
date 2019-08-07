package io.github.strikerrocker.vt.tweaks.sit;

import io.github.strikerrocker.vt.base.Feature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static io.github.strikerrocker.vt.VTModInfo.MODID;

public class Sit extends Feature {
    @ObjectHolder(MODID + ":entity_sit")
    public static final EntityType<EntitySit> SIT_ENTITY_TYPE = null;
    private ForgeConfigSpec.BooleanValue enableSit;

    @Override
    public void setupConfig(ForgeConfigSpec.Builder builder) {
        enableSit = builder
                .translation("config.vanillatweaks:enableSit")
                .comment("Want to be able to sit on slabs and stairs?")
                .define("enableSit", true);
    }

    @Override
    public boolean usesEvents() {
        return true;
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote) {
            World w = event.getWorld();
            BlockPos p = event.getPos();
            BlockState s = w.getBlockState(p);
            Block b = w.getBlockState(p).getBlock();
            PlayerEntity e = event.getEntityPlayer();

            if ((b instanceof SlabBlock || b instanceof StairsBlock) && !EntitySit.OCCUPIED.containsKey(p) && e.getHeldItemMainhand().isEmpty()) {
                if (b instanceof SlabBlock && (!s.has(SlabBlock.TYPE) || s.get(SlabBlock.TYPE) != SlabType.BOTTOM))
                    return;
                else if (b instanceof StairsBlock && (!s.has(StairsBlock.HALF) || s.get(StairsBlock.HALF) != Half.BOTTOM))
                    return;

                EntitySit sit = new EntitySit(w, p);

                w.addEntity(sit);
                e.startRiding(sit);
            }
        }
    }

    @SubscribeEvent
    public void onEntityMount(EntityMountEvent event) {
        if (event.isDismounting()) {
            Entity entity = event.getEntityBeingMounted();
            if (entity instanceof EntitySit) {
                entity.remove();
                EntitySit.OCCUPIED.remove(entity.getPosition());
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (EntitySit.OCCUPIED.containsKey(event.getPos())) {
            EntitySit.OCCUPIED.get(event.getPos()).remove();
            EntitySit.OCCUPIED.remove(event.getPos());
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(EntityType.Builder.<EntitySit>create(EntitySit::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> {
                return SIT_ENTITY_TYPE.create(world);
            }).setTrackingRange(256).setUpdateInterval(20).size(0.0001F, 0.0001F).build(MODID + ":entity_sit").setRegistryName(new ResourceLocation(MODID, "entity_sit")));
        }
    }
}
