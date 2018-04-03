package net.blay09.mods.horsetweaks.client;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityRenderHandler {

    private RenderEnhancedHorse renderer;

    @SubscribeEvent
    public void renderHorse(RenderLivingEvent.Post<AbstractHorse> event) {
        if (!HorseTweaksConfig.renderUpgradesOnHorse || !(event.getEntity() instanceof AbstractHorse)) {
            return;
        }

        if(renderer == null) {
            renderer = new RenderEnhancedHorse(Minecraft.getMinecraft().getRenderManager());
        }

        AbstractHorse horse = (AbstractHorse) event.getEntity();
        renderer.renderUpgrades(horse, event.getX(), event.getY(), event.getZ(), event.getPartialRenderTick());
    }

}