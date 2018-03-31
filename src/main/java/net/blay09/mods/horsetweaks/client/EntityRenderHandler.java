package net.blay09.mods.horsetweaks.client;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityRenderHandler {

    private ModelPlayer modelPlayer = new ModelPlayer(0f, false);

    @SubscribeEvent
    public void renderHorse(RenderLivingEvent.Post<EntityHorse> event) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX(), event.getY(), event.getZ());
        float scale = 0.0625f;
        GlStateManager.scale(scale, scale, scale);
        modelPlayer.render(event.getEntity(), 0f, 0f, 0f, 0f, 0f, 1f);
        GlStateManager.popMatrix();
    }

}