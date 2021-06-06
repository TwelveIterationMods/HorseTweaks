package net.blay09.mods.horsetweaks.client;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID, value = Dist.CLIENT)
public class EntityRenderHandler {

    private static RenderEnhancedHorse renderer;

    @SubscribeEvent
    public static void renderHorse(RenderLivingEvent.Post<AbstractHorseEntity> event) {
        if (!HorseTweaksConfig.renderUpgradesOnHorse || !(event.getEntity() instanceof AbstractHorseEntity)) {
            return;
        }

        if (renderer == null) {
            renderer = new RenderEnhancedHorse(Minecraft.getInstance().getRenderManager());
        }

        AbstractHorseEntity horse = (AbstractHorseEntity) event.getEntity();
        renderer.renderUpgrades(horse, event.getX(), event.getY(), event.getZ(), event.getPartialRenderTick());
    }

}
