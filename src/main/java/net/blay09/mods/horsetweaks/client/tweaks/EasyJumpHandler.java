package net.blay09.mods.horsetweaks.client.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EasyJumpHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();
        if (player != null && player.getRidingEntity() instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse) player.getRidingEntity();
            if ((HorseTweaksConfig.easyJumpByDefault || HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.EASY_JUMP)) && player.horseJumpPowerCounter >= 0) {
                player.horseJumpPower = 1f;
            }
        }

    }

}
