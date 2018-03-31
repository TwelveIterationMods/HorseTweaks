package net.blay09.mods.horsetweaks.client.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SwimmingHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (FMLClientHandler.instance().getClient().isGamePaused()) {
            return;
        }

        EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();
        if (player != null && player.getRidingEntity() instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) player.getRidingEntity();
            if ((horse.isInLava() || horse.isInWater()) && (HorseTweaksConfig.swimmingByDefault || HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.SWIMMING))) {
                horse.addVelocity(0f, 0.0125f, 0f);
            }
        }
    }

}
