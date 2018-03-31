package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LeadNoDropHandler {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!HorseTweaksConfig.returnLeadsIntoInventory) {
            return;
        }

        if (!(event.getEntityPlayer() instanceof FakePlayer) && event.getTarget() instanceof EntityLiving) {
            EntityLiving target = (EntityLiving) event.getTarget();
            if (target.getLeashed() && target.getLeashHolder() == event.getEntityPlayer()) {
                target.clearLeashed(true, false);
                if (!event.getEntityPlayer().addItemStackToInventory(new ItemStack(Items.LEAD))) {
                    target.dropItem(Items.LEAD, 1);
                }
                event.setCanceled(true);
                event.setCancellationResult(EnumActionResult.SUCCESS);
            }
        }
    }

}
