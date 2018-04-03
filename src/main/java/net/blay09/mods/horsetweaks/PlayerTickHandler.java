package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.tweaks.FireWalkerHandler;
import net.blay09.mods.horsetweaks.tweaks.FrostWalkerHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerTickHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        FrostWalkerHandler.onPlayerTick(event);
        FireWalkerHandler.onPlayerTick(event);

        Entity entity = event.player.getRidingEntity();
        if (event.side == Side.SERVER && entity instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse) entity;
            if (horse.isInWater() && horse.ticksExisted % 20 == 0 && HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.SWIMMING)) {
                HorseUpgradeHelper.damageSaddle(horse);
            }
        }
    }

}
