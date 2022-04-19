package io.github.strikerrocker.vt.content;

import io.github.strikerrocker.vt.base.FabricModule;
import io.github.strikerrocker.vt.content.blocks.FabricBlocks;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalRenderer;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalScreen;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteEntity;
import io.github.strikerrocker.vt.misc.EntitySpawnPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import static io.github.strikerrocker.vt.content.blocks.FabricBlocks.PEDESTAL_TYPE;
import static io.github.strikerrocker.vt.content.items.FabricItems.DYNAMITE_TYPE;

public class ClientContentModule extends FabricModule {

    @Override
    public void addFeatures() {
    }

    @Override
    public void initialize() {
        ScreenRegistry.register(FabricBlocks.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);
        BlockEntityRendererRegistry.register(PEDESTAL_TYPE, PedestalRenderer::new);
        EntityRendererRegistry.register(DYNAMITE_TYPE, ThrownItemRenderer::new);
        ClientPlayConnectionEvents.INIT.register((handler, client) -> receiveEntityPacket());
    }

    public void receiveEntityPacket() {
        ClientPlayNetworking.registerReceiver(DynamiteEntity.PACKET_ID, (client, handler, buf, responseSender) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.byId(buf.readVarInt());
            UUID uuid = buf.readUUID();
            int entityId = buf.readVarInt();
            Vec3 pos = EntitySpawnPacket.PacketBufUtil.readVec3d(buf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(buf);
            client.execute(() -> {
                if (Minecraft.getInstance().level == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(Minecraft.getInstance().level);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getKey(et) + "\"!");
                e.setPacketCoordinates(pos);
                e.setPosRaw(pos.x, pos.y, pos.z);
                e.setXRot(pitch);
                e.setYRot(yaw);
                e.setId(entityId);
                e.setUUID(uuid);
                Minecraft.getInstance().level.putNonPlayerEntity(entityId, e);
            });
        });
    }
}