package io.github.strikerrocker.vt.content.blocks.pedestal;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdatePedestal {
    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;

    public PacketUpdatePedestal(BlockPos pos, ItemStack stack, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdatePedestal(PedestalTileEntity te) {
        this(te.getPos(), te.inventory.getStackInSlot(0), te.lastChangeTime);
    }

    public PacketUpdatePedestal() {
    }

    public static void encode(PacketUpdatePedestal message, PacketBuffer packet) {
        message.toBytes(packet);
    }

    public static PacketUpdatePedestal decode(PacketBuffer packet) {
        PacketUpdatePedestal message = new PacketUpdatePedestal();
        message.fromBytes(packet);
        return message;
    }

    public static void onMessage(PacketUpdatePedestal message, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().deferTask(() -> {
            PedestalTileEntity te = (PedestalTileEntity) Minecraft.getInstance().world.getTileEntity(message.pos);
            te.inventory.setStackInSlot(0, message.stack);
            te.lastChangeTime = message.lastChangeTime;
        });
        ctx.get().setPacketHandled(true);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeLong(pos.toLong());
        buf.writeItemStack(stack);
        buf.writeLong(lastChangeTime);
    }

    public void fromBytes(PacketBuffer buf) {
        pos = BlockPos.fromLong(buf.readLong());
        buf.readItemStack();
        lastChangeTime = buf.readLong();
    }
}
