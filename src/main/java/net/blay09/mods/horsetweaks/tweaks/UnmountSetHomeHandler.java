package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UnmountSetHomeHandler {

    @SubscribeEvent
    public void onUnmount(EntityMountEvent event) {
        if (!HorseTweaksConfig.setHomeOnDismount) {
            return;
        }

        if (event.isDismounting() && event.getEntityBeingMounted() instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) event.getEntityBeingMounted();
            if (horse.isTame() && horse.isHorseSaddled()) {
                horse.setHomePosAndDistance(event.getEntityBeingMounted().getPosition(), 16);
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!HorseTweaksConfig.setHomeOnDismount) {
            return;
        }
        if (event.getItemStack().getItem() == Items.LEAD && event.getTarget() instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) event.getTarget();
            if (horse.hasHome()) {
                horse.detachHome();
            }
        }
    }

}
