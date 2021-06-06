package net.blay09.mods.horsetweaks.network;

import net.blay09.mods.horsetweaks.mixin.AbstractHorseEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HorseDataMessage {

    public final int entityId;
    public final ItemStack saddle;
    public final ItemStack armor;

    public HorseDataMessage(int entityId, ItemStack saddle, ItemStack armor) {
        this.entityId = entityId;
        this.saddle = saddle;
        this.armor = armor;
    }

    public static void encode(HorseDataMessage message, PacketBuffer buf) {
        buf.writeInt(message.entityId);
        buf.writeItemStack(message.saddle);
        buf.writeItemStack(message.armor);
    }

    public static HorseDataMessage decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        ItemStack saddle = buf.readItemStack();
        ItemStack armor = buf.readItemStack();
        return new HorseDataMessage(entityId, saddle, armor);
    }

    public static void handle(HorseDataMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                World world = Minecraft.getInstance().world;
                Entity entity = world != null ? world.getEntityByID(message.entityId) : null;
                if (entity instanceof AbstractHorseEntityAccessor) {
                    AbstractHorseEntityAccessor horse = (AbstractHorseEntityAccessor) entity;
                    horse.getHorseChest().setInventorySlotContents(0, message.saddle);
                    horse.getHorseChest().setInventorySlotContents(1, message.armor);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
