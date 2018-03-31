package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.network.HorseDataMessage;
import net.blay09.mods.horsetweaks.network.NetworkHandler;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SyncHorseDataHandler {

    @SubscribeEvent
    public void onHorseSpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) event.getEntity();
            if (!event.getWorld().isRemote) {
                horse.horseChest.addInventoryChangeListener(invBasic -> {
                    if (!horse.world.isRemote) {
                        EntityTracker tracker = ((WorldServer) horse.world).getEntityTracker();
                        for (EntityPlayer player : tracker.getTrackingPlayers(horse)) {
                            NetworkHandler.instance.sendTo(getHorseDataMessage(horse), (EntityPlayerMP) player);
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) event.getTarget();
            NetworkHandler.instance.sendTo(getHorseDataMessage(horse), (EntityPlayerMP) event.getEntityPlayer());
        }
    }

    private HorseDataMessage getHorseDataMessage(EntityHorse horse) {
        return new HorseDataMessage(horse.getEntityId(), horse.horseChest.getStackInSlot(0));
    }
}
