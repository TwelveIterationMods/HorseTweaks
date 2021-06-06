package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class ThornsHandler {

    private static final Random random = new Random();

    @SubscribeEvent
    public static void onHorseDamaged(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            entity = entity.getRidingEntity();
        }
        if (entity instanceof AbstractHorseEntity && HorseUpgradeHelper.hasUpgrade((AbstractHorseEntity) entity, HorseUpgrade.THORNS)) {
            event.setAmount(event.getAmount() * 0.5f);
            DamageSource source = event.getSource();
            Entity attacker = source.getImmediateSource();
            if (attacker != null) {
                attacker.attackEntityFrom(DamageSource.causeThornsDamage(entity), 1 + random.nextInt(4));
                HorseUpgradeHelper.damageSaddle((AbstractHorseEntity) entity);
            }
        }
    }

}
