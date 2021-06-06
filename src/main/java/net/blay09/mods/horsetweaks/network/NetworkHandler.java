package net.blay09.mods.horsetweaks.network;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String version = "1.0";

    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(HorseTweaks.MOD_ID, "network"), () -> version, it -> it.equals(version), it -> it.equals(version));

    public static void init() {
        channel.registerMessage(0, HorseDataMessage.class, HorseDataMessage::encode, HorseDataMessage::decode, HorseDataMessage::handle);
    }

    public static void sendTo(PlayerEntity player, Object message) {
        channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), message);
    }

}
