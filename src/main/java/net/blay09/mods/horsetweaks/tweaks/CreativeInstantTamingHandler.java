package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class CreativeInstantTamingHandler {

    @SubscribeEvent
    public static void onMountHorse(EntityMountEvent event) {
        if (!HorseTweaksConfig.instantTameInCreative) {
            return;
        }

        if (event.getEntityMounting() instanceof PlayerEntity && event.getEntityBeingMounted() instanceof AbstractHorseEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityMounting();
            AbstractHorseEntity horse = (AbstractHorseEntity) event.getEntityBeingMounted();
            if (player.abilities.isCreativeMode && !horse.isTame()) {
                horse.setTamedBy(player);
            }
        }
    }

}
