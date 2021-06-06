package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class FeatherFallHandler {

    @SubscribeEvent
    public static void onHorseDamaged(LivingDamageEvent event) {
        if (event.getSource() != DamageSource.FALL) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            entity = entity.getRidingEntity();
        }
        if (entity instanceof AbstractHorseEntity && (HorseTweaksConfig.featherFallByDefault || HorseUpgradeHelper.hasUpgrade((AbstractHorseEntity) entity, HorseUpgrade.FEATHER_FALL))) {
            event.setAmount(0f);
            HorseUpgradeHelper.damageSaddle((AbstractHorseEntity) entity);
        }
    }

}
