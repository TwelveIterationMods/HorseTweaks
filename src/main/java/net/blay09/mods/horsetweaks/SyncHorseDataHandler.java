package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.mixin.AbstractHorseEntityAccessor;
import net.blay09.mods.horsetweaks.network.HorseDataMessage;
import net.blay09.mods.horsetweaks.network.NetworkHandler;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class SyncHorseDataHandler {

    @SubscribeEvent
    public static void onHorseSpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) event.getEntity();
            if (!event.getWorld().isRemote) {
                if (HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.FIRE_RESISTANCE)) {
                    horse.isImmuneToFire = true;
                }

                horse.horseChest.addInventoryChangeListener(invBasic -> {
                    if (!horse.world.isRemote) {
                        EntityTracker tracker = ((ServerWorld) horse.world).getEntityTracker();
                        for (PlayerEntity player : tracker.getTrackingPlayers(horse)) {
                            NetworkHandler.sendTo(player, getHorseDataMessage(horse));
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
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) event.getTarget();
            NetworkHandler.sendTo(event.getPlayer(), getHorseDataMessage(horse));
        }
    }

    private static HorseDataMessage getHorseDataMessage(AbstractHorseEntity horse) {
        Inventory horseChest = ((AbstractHorseEntityAccessor) horse).getHorseChest();
        return new HorseDataMessage(horse.getEntityId(), horseChest.getStackInSlot(0), horseChest.getStackInSlot(1));
    }
}
