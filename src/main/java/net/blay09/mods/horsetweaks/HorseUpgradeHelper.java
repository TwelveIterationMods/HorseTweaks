package net.blay09.mods.horsetweaks;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.asm.transformers.ItemStackTransformer;

import java.util.ArrayList;
import java.util.List;

public class HorseUpgradeHelper {

    private static final String HORSE_TWEAKS_UPGRADES_TAG = "HorseTweaksUpgrades";
    private static final String VANILLA_ENCHANTMENTS = "ench";

    private static NBTTagCompound getHorseTweaksUpgrades(ItemStack saddle, boolean createIfNecessary) {
        NBTTagCompound compound = saddle.getTagCompound();
        if (compound == null) {
            if (createIfNecessary) {
                compound = new NBTTagCompound();
                saddle.setTagCompound(compound);
            } else {
                return new NBTTagCompound();
            }
        }
        NBTTagCompound upgrades = compound.getCompoundTag(HORSE_TWEAKS_UPGRADES_TAG);
        if (createIfNecessary) {
            compound.setTag(HORSE_TWEAKS_UPGRADES_TAG, upgrades);
        }
        return upgrades;
    }

    public static boolean hasUpgrade(ItemStack saddle, HorseUpgrade upgrade) {
        NBTTagCompound upgrades = getHorseTweaksUpgrades(saddle, false);
        return upgrades.getBoolean(upgrade.name());
    }

    public static ItemStack applyUpgrade(ItemStack saddle, HorseUpgrade upgrade) {
        NBTTagCompound upgrades = getHorseTweaksUpgrades(saddle, true);

        // Apply a dummy enchantment to get the glimmer effect
        /*NBTTagCompound compound = saddle.getTagCompound();
        if (compound != null) {
            NBTTagList ench = compound.getTagList(VANILLA_ENCHANTMENTS, Constants.NBT.TAG_COMPOUND);
            if (ench.tagCount() == 0) {
                ench.appendTag(new NBTTagCompound());
            }
            compound.setTag(VANILLA_ENCHANTMENTS, ench);
        }*/

        upgrades.setBoolean(upgrade.name(), true);
        return saddle;
    }

    public static List<HorseUpgrade> getUpgrades(ItemStack saddle) {
        NBTTagCompound compound = getHorseTweaksUpgrades(saddle, false);
        List<HorseUpgrade> result = new ArrayList<>();
        for (HorseUpgrade upgrade : HorseUpgrade.values) {
            if (compound.getBoolean(upgrade.name())) {
                result.add(upgrade);
            }
        }
        return result;
    }

    public static boolean hasUpgrade(EntityHorse horse, HorseUpgrade upgrade) {
        ItemStack saddle = horse.horseChest.getStackInSlot(0);
        return hasUpgrade(saddle, upgrade);
    }

    public static void damageSaddle(EntityHorse horse) {
        if (HorseTweaksConfig.saddleDurability == 0) {
            return;
        }

        ItemStack saddle = horse.horseChest.getStackInSlot(0);
        Entity passenger = horse.getControllingPassenger();
        saddle.damageItem(1, passenger instanceof EntityPlayer ? (EntityPlayer) passenger : horse);
        if (saddle.getItemDamage() >= saddle.getMaxDamage()) {
            horse.horseChest.markDirty();
        }
    }

}
