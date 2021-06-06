package net.blay09.mods.horsetweaks.client;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.RenderAbstractHorse;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.EnumSet;

public class RenderEnhancedHorse extends AbstractHorseRenderer {

    private static final ModelSaddleUpgrades model = new ModelSaddleUpgrades();
    private static final ResourceLocation texture = new ResourceLocation(HorseTweaks.MOD_ID, "textures/entity/horse_upgrades.png");

    private final ItemStack FEATHER = new ItemStack(Items.FEATHER);
    private final ItemStack CACTUS = new ItemStack(Blocks.CACTUS);
    private final ItemStack LEAVES = new ItemStack(Blocks.LEAVES);
    private final ItemStack MAGMA_CREAM = new ItemStack(Items.MAGMA_CREAM);

    public RenderEnhancedHorse(RenderManager renderManager) {
        super(renderManager);
    }

    public void renderUpgrades(AbstractHorse entity, double x, double y, double z, float partialTicks) {
        ItemStack saddle = entity.horseChest.getStackInSlot(0);
        EnumSet<HorseUpgrade> upgradeList = HorseUpgradeHelper.getUpgrades(saddle);
        if (upgradeList.isEmpty()) {
            return;
        }

        float interpolatedYaw = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float handledRotationFloat = handleRotationFloat(entity, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        applyRotations(entity, handledRotationFloat, interpolatedYaw, partialTicks);
        bindTexture(texture);

        if (upgradeList.contains(HorseUpgrade.EASY_JUMP)) {
            renderEasyJump();
        }

        if (upgradeList.contains(HorseUpgrade.FROST_WALKER)) {
            renderFrostWalker();
        }

        if (upgradeList.contains(HorseUpgrade.SWIMMING)) {
            renderSwimming();
        }

        if (upgradeList.contains(HorseUpgrade.FEATHER_FALL)) {
            renderFeatherFall();
        }

        if (upgradeList.contains(HorseUpgrade.LEAF_WALKER)) {
            renderLeafWalker();
        }

        if (upgradeList.contains(HorseUpgrade.THORNS)) {
            renderThorns();
        }

        if (upgradeList.contains(HorseUpgrade.FIRE_RESISTANCE)) {
            renderFireResistance();
        }

        GlStateManager.popMatrix();
    }

    public void renderFeatherFall() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.33f, 1.45f, 0.4f);
        GlStateManager.rotate(90f, 0f, 1f, 0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().getRenderItem().renderItem(FEATHER, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.33f, 1.45f, 0.4f);
        GlStateManager.rotate(90f, 0f, 1f, 0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().getRenderItem().renderItem(FEATHER, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    public void renderEasyJump() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 0.3075f, -0.001f);
        GlStateManager.scale(1.01f, 1.01f, 1.01f);
        model.renderFootMetals();
        GlStateManager.popMatrix();
    }

    public void renderSwimming() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 0.4f, 0f);
        GlStateManager.scale(0.8f, 1f, 0.8f);
        model.renderSwimmingPlanks();
        GlStateManager.popMatrix();
    }

    public void renderThorns() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.25f, 1.3f, 0.8f);
        GlStateManager.rotate(135f, 0f, 1f, 0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().getRenderItem().renderItem(CACTUS, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.25f, 1.3f, 0.8f);
        GlStateManager.rotate(135f, 0f, 1f, 0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().getRenderItem().renderItem(CACTUS, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    public void renderLeafWalker() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 1f, -0.3f);
        GlStateManager.rotate(90f, 0f, 1f, 0f);
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        Minecraft.getMinecraft().getRenderItem().renderItem(LEAVES, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 1f, 0.45f);
        GlStateManager.rotate(90f, 0f, 1f, 0f);
        GlStateManager.scale(1.5f, 1.5f, 1.5f);
        Minecraft.getMinecraft().getRenderItem().renderItem(LEAVES, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

    public void renderFrostWalker() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 1.51f, 0f);
        GlStateManager.rotate(180f, 0f, 0f, 1f);
        GlStateManager.scale(1.101f, 1f, 1.025f);
        model.renderMainSaddle();
        GlStateManager.popMatrix();
    }

    public void renderFireResistance() {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, 1.225f, 0.55f);
        GlStateManager.rotate(90f, 0f, 1f, 0f);
        GlStateManager.scale(1.1f, 1f, 1.25f);
        Minecraft.getMinecraft().getRenderItem().renderItem(MAGMA_CREAM, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }

}
