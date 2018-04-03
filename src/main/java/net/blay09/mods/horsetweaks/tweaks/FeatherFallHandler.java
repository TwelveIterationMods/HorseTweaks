package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatherFallHandler {

    @SubscribeEvent
    public void onHorseDamaged(LivingDamageEvent event) {
        if (event.getSource() != DamageSource.FALL) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            entity = entity.getRidingEntity();
        }
        if (entity instanceof AbstractHorse && (HorseTweaksConfig.featherFallByDefault || HorseUpgradeHelper.hasUpgrade((AbstractHorse) entity, HorseUpgrade.FEATHER_FALL))) {
            event.setAmount(0f);
            HorseUpgradeHelper.damageSaddle((AbstractHorse) entity);
        }
    }

}
