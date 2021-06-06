package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class UnmountSetHomeHandler {

    @SubscribeEvent
    public static void onUnmount(EntityMountEvent event) {
        if (!HorseTweaksConfig.setHomeOnDismount) {
            return;
        }

        if (event.isDismounting() && event.getEntityBeingMounted() instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) event.getEntityBeingMounted();
            if (horse.isTame() && horse.isHorseSaddled()) {
                horse.setHomePosAndDistance(event.getEntityBeingMounted().getPosition(), 16);
            }
        }
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!HorseTweaksConfig.setHomeOnDismount) {
            return;
        }

        if (event.getItemStack().getItem() == Items.LEAD && event.getTarget() instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) event.getTarget();
            if (horse.hasHome()) {
                horse.detachHome();
            }
        }
    }

}
