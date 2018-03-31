package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SaddleRightClickHandler {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!HorseTweaksConfig.addSaddleOnRightClick) {
            return;
        }

        if (event.getTarget() instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) event.getTarget();
            if (HorseTweaksConfig.instantTameInCreative && !horse.isTame() && !(event.getEntityPlayer() instanceof FakePlayer) && event.getEntityPlayer().capabilities.isCreativeMode) {
                horse.setTamedBy(event.getEntityPlayer());
            }
            if (horse.isTame()) {
                if (event.getItemStack().getItem() instanceof ItemSaddle) {
                    if (horse.horseChest.getStackInSlot(0).isEmpty()) {
                        ItemStack saddle = event.getItemStack().splitStack(1);
                        horse.horseChest.setInventorySlotContents(0, saddle);
                        event.setCanceled(true);
                        event.setCancellationResult(EnumActionResult.SUCCESS);
                    }
                } else if (HorseArmorType.isHorseArmor(event.getItemStack().getItem())) {
                    if (horse.horseChest.getStackInSlot(1).isEmpty()) {
                        ItemStack horseArmor = event.getItemStack().splitStack(1);
                        horse.horseChest.setInventorySlotContents(1, horseArmor);
                        event.setCanceled(true);
                        event.setCancellationResult(EnumActionResult.SUCCESS);
                    }
                }
            }
        }
    }

}
