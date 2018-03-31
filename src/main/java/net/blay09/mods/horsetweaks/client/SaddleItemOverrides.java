package net.blay09.mods.horsetweaks.client;

import com.google.common.collect.ImmutableMap;
import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ItemLayerModel;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class SaddleItemOverrides extends ItemOverrideList {

    private Map<EnumSet<HorseUpgrade>, IBakedModel> modelCache = new HashMap<>();

    public SaddleItemOverrides(List<ItemOverride> overridesIn) {
        super(overridesIn);
    }

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
        EnumSet<HorseUpgrade> upgradeList = HorseUpgradeHelper.getUpgrades(stack);
        IBakedModel cachedModel = modelCache.get(upgradeList);
        if (cachedModel != null) {
            return cachedModel;
        }

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("layer0", "minecraft:items/saddle");
        int layer = 1;
        for (HorseUpgrade upgrade : upgradeList) {
            builder.put("layer" + layer, HorseTweaks.MOD_ID + ":items/" + upgrade.name().toLowerCase(Locale.ENGLISH));
            layer++;
        }
        ItemLayerModel newModel = ItemLayerModel.INSTANCE.retexture(builder.build());
        Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        cachedModel = newModel.bake(newModel.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
        modelCache.put(upgradeList, cachedModel);
        return cachedModel;
    }

}
