package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class LeadNoDropHandler {

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!HorseTweaksConfig.returnLeadsIntoInventory) {
            return;
        }

        if (!(event.getPlayer() instanceof FakePlayer) && event.getTarget() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) event.getTarget();
            if (target.getLeashed() && target.getLeashHolder() == event.getPlayer()) {
                target.clearLeashed(true, false);
                if (!event.getPlayer().addItemStackToInventory(new ItemStack(Items.LEAD))) {
                    target.dropItem(Items.LEAD, 1);
                }
                event.setCanceled(true);
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }
    }

}
