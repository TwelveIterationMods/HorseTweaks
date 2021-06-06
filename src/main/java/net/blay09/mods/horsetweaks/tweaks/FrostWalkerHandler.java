package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class FrostWalkerHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Entity entity = event.player.getRidingEntity();
        if (event.phase == TickEvent.Phase.END && entity instanceof AbstractHorseEntity && HorseUpgradeHelper.hasUpgrade((AbstractHorseEntity) entity, HorseUpgrade.FROST_WALKER)) {
            FrostWalkerEnchantment.freezeNearby((AbstractHorseEntity) entity, entity.world, entity.getPosition(), 1);

            if (entity.ticksExisted % 20 == 0 && entity.world.getBlockState(entity.getPosition().down()).getBlock() == Blocks.FROSTED_ICE) {
                HorseUpgradeHelper.damageSaddle((AbstractHorseEntity) entity);
            }
        }
    }

}
