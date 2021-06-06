package net.blay09.mods.horsetweaks.client;

import com.google.common.collect.ImmutableList;
import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Locale;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID, value = Dist.CLIENT)
public class ModTextures {
    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        for (HorseUpgrade upgrade : HorseUpgrade.values) {
            event.getMap().registerSprite(new ResourceLocation(HorseTweaks.MOD_ID, "items/" + upgrade.name().toLowerCase(Locale.ENGLISH)));
        }
    }

    @SubscribeEvent
    public static void bakeModels(ModelBakeEvent event) {
        ItemLayerModel model = new ItemLayerModel(ImmutableList.of(new ResourceLocation("minecraft", "items/saddle")), new SaddleItemOverrides(Collections.emptyList()));
        Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getInstance().getTextureMapBlocks().getAtlasSprite(location.toString());
        IBakedModel bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
        event.getModelRegistry().putObject(new ModelResourceLocation(new ResourceLocation(HorseTweaks.MOD_ID, "modded_saddle"), "inventory"), bakedModel);
    }
}
