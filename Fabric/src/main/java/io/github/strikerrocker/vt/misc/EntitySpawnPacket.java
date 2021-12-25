package io.github.strikerrocker.vt.misc;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntitySpawnPacket {
    public static Packet<?> create(Entity e, ResourceLocation packetID) {
        if (e.level.isClientSide)
            throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(Registry.ENTITY_TYPE.getId(e.getType()));
        byteBuf.writeUUID(e.getUUID());
        byteBuf.writeVarInt(e.getId());

        PacketBufUtil.writeVec3d(byteBuf, e.position());
        PacketBufUtil.writeAngle(byteBuf, e.getXRot());
        PacketBufUtil.writeAngle(byteBuf, e.getYRot());
        return ServerPlayNetworking.createS2CPacket(packetID, byteBuf);
    }

    public static final class PacketBufUtil {
        public static byte packAngle(float angle) {
            return (byte) Mth.floor(angle * 256 / 360);
        }

        public static float unpackAngle(byte angleByte) {
            return (angleByte * 360) / 256f;
        }

        public static void writeAngle(FriendlyByteBuf byteBuf, float angle) {
            byteBuf.writeByte(packAngle(angle));
        }

        public static float readAngle(FriendlyByteBuf byteBuf) {
            return unpackAngle(byteBuf.readByte());
        }

        public static void writeVec3d(FriendlyByteBuf byteBuf, Vec3 vec3d) {
            byteBuf.writeDouble(vec3d.x);
            byteBuf.writeDouble(vec3d.y);
            byteBuf.writeDouble(vec3d.z);
        }

        public static Vec3 readVec3d(FriendlyByteBuf byteBuf) {
            double x = byteBuf.readDouble();
            double y = byteBuf.readDouble();
            double z = byteBuf.readDouble();
            return new Vec3(x, y, z);
        }
    }
}
