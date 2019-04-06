package net.blay09.mods.horsetweaks.network;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

public class HorseDataHandler implements IMessageHandler<HorseDataMessage, IMessage> {

    @Override
    @Nullable
    public IMessage onMessage(HorseDataMessage message, MessageContext ctx) {
        NetworkHandler.getThreadListener(ctx).addScheduledTask(() -> {
            HorseTweaks.proxy.receivedHorseData(message.entityId, message.saddle, message.armor);
        });
        return null;
    }

}
