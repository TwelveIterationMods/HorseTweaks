package net.blay09.mods.horsetweaks.client;

import com.google.common.collect.ImmutableList;
import net.blay09.mods.horsetweaks.CommonProxy;
import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.client.tweaks.EasyJumpHandler;
import net.blay09.mods.horsetweaks.client.tweaks.SwimmingHandler;
import net.blay09.mods.horsetweaks.tweaks.SaddleTooltipHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.Locale;
import java.util.function.Function;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
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
        if (entity instanceof AbstractHorse) {
            AbstractHorse horse = (AbstractHorse) entity;
            horse.horseChest.setInventorySlotContents(0, saddle);
        }
    }

    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event) {
        for (HorseUpgrade upgrade : HorseUpgrade.values) {
            event.getMap().registerSprite(new ResourceLocation(HorseTweaks.MOD_ID, "items/" + upgrade.name().toLowerCase(Locale.ENGLISH)));
        }
    }

    @SubscribeEvent
    public void bakeModels(ModelBakeEvent event) {
        ItemLayerModel model = new ItemLayerModel(ImmutableList.of(new ResourceLocation("minecraft", "items/saddle")), new SaddleItemOverrides(Collections.emptyList()));
        Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        IBakedModel bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
        event.getModelRegistry().putObject(new ModelResourceLocation(new ResourceLocation(HorseTweaks.MOD_ID, "modded_saddle"), "inventory"), bakedModel);
    }

}
