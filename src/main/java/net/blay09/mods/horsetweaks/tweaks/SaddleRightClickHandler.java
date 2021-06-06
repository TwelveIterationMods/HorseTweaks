package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class SaddleRightClickHandler {

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!HorseTweaksConfig.addSaddleOnRightClick) {
            return;
        }

        if (event.getTarget() instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) event.getTarget();
            if (HorseTweaksConfig.instantTameInCreative && !horse.isTame() && !(event.getPlayer() instanceof FakePlayer) && event.getPlayer().abilities.isCreativeMode) {
                horse.setTamedBy(event.getPlayer());
            }
            if (horse.isTame()) {
                if (event.getItemStack().getItem() instanceof SaddleItem) {
                    if (horse.horseChest.getStackInSlot(0).isEmpty()) {
                        ItemStack saddle = event.getItemStack().split(1);
                        horse.horseChest.setInventorySlotContents(0, saddle);
                        event.setCanceled(true);
                        event.setCancellationResult(ActionResultType.SUCCESS);
                    }
                } else if (horse.wearsArmor() && HorseArmorType.isHorseArmor(event.getItemStack().getItem())) {
                    if (horse.horseChest.getStackInSlot(1).isEmpty()) {
                        ItemStack horseArmor = event.getItemStack().split(1);
                        horse.horseChest.setInventorySlotContents(1, horseArmor);
                        event.setCanceled(true);
                        event.setCancellationResult(ActionResultType.SUCCESS);
                    }
                }
            }
        }
    }

}
