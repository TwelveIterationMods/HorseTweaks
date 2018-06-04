package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.network.HorseDataMessage;
import net.blay09.mods.horsetweaks.network.NetworkHandler;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SyncHorseDataHandler {

    @SubscribeEvent
    public void onHorseSpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse) event.getEntity();
            if (!event.getWorld().isRemote) {
                if (HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.FIRE_RESISTANCE)) {
                    horse.isImmuneToFire = true;
                }

                horse.horseChest.addInventoryChangeListener(invBasic -> {
                    if (!horse.world.isRemote) {
                        EntityTracker tracker = ((WorldServer) horse.world).getEntityTracker();
                        for (EntityPlayer player : tracker.getTrackingPlayers(horse)) {
                            NetworkHandler.instance.sendTo(getHorseDataMessage(horse), (EntityPlayerMP) player);
                        }

                        if (!HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.FIRE_RESISTANCE)) {
                            horse.isImmuneToFire = false;
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse) event.getTarget();
            NetworkHandler.instance.sendTo(getHorseDataMessage(horse), (EntityPlayerMP) event.getEntityPlayer());
        }
    }

    private HorseDataMessage getHorseDataMessage(AbstractHorse horse) {
        return new HorseDataMessage(horse.getEntityId(), horse.horseChest.getStackInSlot(0));
    }
}
