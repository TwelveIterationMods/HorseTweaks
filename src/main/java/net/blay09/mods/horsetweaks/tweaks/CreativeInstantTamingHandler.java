package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CreativeInstantTamingHandler {

    @SubscribeEvent
    public void onMountHorse(EntityMountEvent event) {
        if (!HorseTweaksConfig.instantTameInCreative) {
            return;
        }

        if (event.getEntityMounting() instanceof EntityPlayer && event.getEntityBeingMounted() instanceof AbstractHorse) {
            EntityPlayer player = (EntityPlayer) event.getEntityMounting();
            AbstractHorse horse = (AbstractHorse) event.getEntityBeingMounted();
            if (player.capabilities.isCreativeMode && !horse.isTame()) {
                horse.setTamedBy(player);
            }
        }
    }

}
