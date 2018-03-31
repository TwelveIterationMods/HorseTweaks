package net.blay09.mods.horsetweaks.client;

import net.blay09.mods.horsetweaks.CommonProxy;
import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.client.tweaks.EasyJumpHandler;
import net.blay09.mods.horsetweaks.client.tweaks.SwimmingHandler;
import net.blay09.mods.horsetweaks.tweaks.SaddleTooltipHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new EntityRenderHandler());
        MinecraftForge.EVENT_BUS.register(new SaddleTooltipHandler());
        MinecraftForge.EVENT_BUS.register(new EasyJumpHandler());
        MinecraftForge.EVENT_BUS.register(new SwimmingHandler());
    }

    @Override
    public void init() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(HorseTweaks.MOD_ID, "modded_saddle"), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Items.SADDLE, 0, location);
    }

    @Override
    public void receivedHorseData(int entityId, ItemStack saddle) {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if (entity instanceof EntityHorse) {
            EntityHorse horse = (EntityHorse) entity;
            horse.horseChest.setInventorySlotContents(0, saddle);
        }
    }

}
