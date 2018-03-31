package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FrostWalkerHandler {

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Entity entity = event.player.getRidingEntity();
        if (entity instanceof EntityHorse && HorseUpgradeHelper.hasUpgrade((EntityHorse) entity, HorseUpgrade.FROST_WALKER)) {
            EnchantmentFrostWalker.freezeNearby((EntityHorse) entity, entity.world, entity.getPosition(), 1);

            if (entity.ticksExisted % 20 == 0 && entity.world.getBlockState(entity.getPosition().down()).getBlock() == Blocks.FROSTED_ICE) {
                HorseUpgradeHelper.damageSaddle((EntityHorse) entity);
            }
        }
    }

}
