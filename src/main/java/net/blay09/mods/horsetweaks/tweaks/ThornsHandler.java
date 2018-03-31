package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ThornsHandler {

    private static final Random random = new Random();

    @SubscribeEvent
    public void onHorseDamaged(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            entity = entity.getRidingEntity();
        }
        if (entity instanceof EntityHorse && HorseUpgradeHelper.hasUpgrade((EntityHorse) entity, HorseUpgrade.THORNS)) {
            event.setAmount(event.getAmount() * 0.5f);
            DamageSource source = event.getSource();
            Entity attacker = source.getImmediateSource();
            if (attacker != null) {
                attacker.attackEntityFrom(DamageSource.causeThornsDamage(entity), 1 + random.nextInt(4));
                HorseUpgradeHelper.damageSaddle((EntityHorse) entity);
            }
        }
    }

}
