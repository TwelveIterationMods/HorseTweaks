package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.tweaks.FireResistanceHandler;
import net.blay09.mods.horsetweaks.tweaks.FrostWalkerHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerTickHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        FrostWalkerHandler.onPlayerTick(event);
        FireResistanceHandler.onPlayerTick(event);

        Entity entity = event.player.getRidingEntity();
        if (entity instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) entity;
            if (horse.isInWater() && horse.ticksExisted % 20 == 0 && HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.SWIMMING)) {
                HorseUpgradeHelper.damageSaddle(horse);
            }

            if (horse.isHorseJumping() && horse.ticksExisted % 10 == 0 && HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.EASY_JUMP)) {
                HorseUpgradeHelper.damageSaddle(horse);
            }
        }
    }

}
