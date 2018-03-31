package net.blay09.mods.horsetweaks.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSaddleUpgrades extends ModelBase {

    public final ModelRenderer horseSaddleBottom;
    public final ModelRenderer horseSaddleFront;
    public final ModelRenderer horseSaddleBack;

    public final ModelRenderer horseLeftSaddleMetal;
    public final ModelRenderer horseRightSaddleMetal;

    public final ModelRenderer horseSwimmingPlanks;

    public ModelSaddleUpgrades() {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.horseSaddleBottom = new ModelRenderer(this, 80, 0);
        this.horseSaddleBottom.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8);
        this.horseSaddleBottom.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.horseSaddleFront = new ModelRenderer(this, 106, 9);
        this.horseSaddleFront.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2);
        this.horseSaddleFront.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.horseSaddleBack = new ModelRenderer(this, 80, 9);
        this.horseSaddleBack.addBox(-4.0F, -1.0F, 2.85F, 8, 1, 2);
        this.horseSaddleBack.setRotationPoint(0.0F, 2.0F, 2.0F);

        this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0);
        this.horseLeftSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
        this.horseLeftSaddleMetal.setRotationPoint(5.0F, 3.0F, 2.0F);

        this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4);
        this.horseRightSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
        this.horseRightSaddleMetal.setRotationPoint(-5.0F, 3.0F, 2.0F);

        this.horseSwimmingPlanks = new ModelRenderer(this, 0, 34);
        this.horseSwimmingPlanks.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
        this.horseSwimmingPlanks.setRotationPoint(0.0F, 11.0F, 9.0F);
    }

    public void renderMainSaddle() {
        float scale = 0.0625f;
        this.horseSaddleBottom.renderWithRotation(scale);
        this.horseSaddleFront.renderWithRotation(scale);
        this.horseSaddleBack.renderWithRotation(scale);
    }

    public void renderFootMetals() {
        float scale = 0.0625f;
        this.horseLeftSaddleMetal.renderWithRotation(scale);
        this.horseRightSaddleMetal.renderWithRotation(scale);
    }

    public void renderSwimmingPlanks() {
        float scale = 0.0625f;
        this.horseSwimmingPlanks.renderWithRotation(scale);
    }

}
