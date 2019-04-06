package net.blay09.mods.horsetweaks.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class HorseDataMessage implements IMessage {

    public int entityId;
    public ItemStack saddle;
    public ItemStack armor;

    public HorseDataMessage() {
    }

    public HorseDataMessage(int entityId, ItemStack saddle, ItemStack armor) {
        this.entityId = entityId;
        this.saddle = saddle;
        this.armor = armor;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        saddle = ByteBufUtils.readItemStack(buf);
        armor = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        ByteBufUtils.writeItemStack(buf, saddle);
        ByteBufUtils.writeItemStack(buf, armor);
    }

}
