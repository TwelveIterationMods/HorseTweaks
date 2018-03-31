package net.blay09.mods.horsetweaks.network;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkHandler {

    public static SimpleNetworkWrapper instance = new SimpleNetworkWrapper(HorseTweaks.MOD_ID);

    public static void init() {
        instance.registerMessage(HorseDataHandler.class, HorseDataMessage.class, 0, Side.CLIENT);
    }

    public static IThreadListener getThreadListener(MessageContext ctx) {
        return ctx.side == Side.SERVER ? (WorldServer) ctx.getServerHandler().player.world : getClientThreadListener();
    }

    @SideOnly(Side.CLIENT)
    public static IThreadListener getClientThreadListener() {
        return Minecraft.getMinecraft();
    }

}
