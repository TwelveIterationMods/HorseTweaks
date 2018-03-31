package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemSaddle;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RejectPigSaddlesHandler {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof EntityPig) {
            if (event.getItemStack().getItem() instanceof ItemSaddle) {
                if(!HorseUpgradeHelper.getUpgrades(event.getItemStack()).isEmpty()) {
                    event.setCanceled(true);
                    event.setCancellationResult(EnumActionResult.SUCCESS);
                }
            }
        }
    }

}
