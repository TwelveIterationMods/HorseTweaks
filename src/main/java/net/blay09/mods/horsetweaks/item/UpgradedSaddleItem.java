package net.blay09.mods.horsetweaks.item;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.minecraft.item.Item;
import net.minecraft.item.SaddleItem;
import net.minecraft.util.ResourceLocation;

public class UpgradedSaddleItem extends SaddleItem {

    public static final String name = "upgraded_saddle";
    public static final ResourceLocation registryName = new ResourceLocation(HorseTweaks.MOD_ID, name);

    public UpgradedSaddleItem() {
        super(new Item.Properties()
                .group(HorseTweaks.itemGroup)
                .setNoRepair()
                .maxDamage(HorseTweaksConfig.COMMON.saddleDurability.get()));
    }
}
