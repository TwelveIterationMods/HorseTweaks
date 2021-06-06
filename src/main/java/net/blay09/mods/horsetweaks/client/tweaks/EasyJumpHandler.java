package net.blay09.mods.horsetweaks.client.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID, value = Dist.CLIENT)
public class EasyJumpHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.getRidingEntity() instanceof AbstractHorseEntity) {
            AbstractHorseEntity horse = (AbstractHorseEntity) player.getRidingEntity();
            if ((HorseTweaksConfig.COMMON.easyJumpByDefault.get() || HorseUpgradeHelper.hasUpgrade(horse, HorseUpgrade.EASY_JUMP)) && player.horseJumpPowerCounter >= 0) {
                player.jumpPower = 1f;
            }
        }

    }

}
