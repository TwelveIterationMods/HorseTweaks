package net.blay09.mods.horsetweaks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class PlayerTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        Entity entity = event.player.getRidingEntity();
        if (event.side == LogicalSide.SERVER && entity instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) entity;
            if (horse.isInWater() && horse.ticksExisted % 20 == 0 && HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.SWIMMING)) {
                HorseUpgradeHelper.damageSaddle(horse);
            }

            if (!horse.isOnGround() && horse.fallDistance == 0f && horse.ticksExisted % 5 == 0 && HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.EASY_JUMP)) {
                HorseUpgradeHelper.damageSaddle(horse);
            }
        }
    }

}
