package net.blay09.mods.horsetweaks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.EnumSet;

public class HorseUpgradeHelper {

    private static final String HORSE_TWEAKS_UPGRADES_TAG = "HorseTweaksUpgrades";

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
        upgrades.setBoolean(upgrade.name(), true);
        return saddle;
    }

    private static final EnumSet<HorseUpgrade> EMPTY_UPGRADES = EnumSet.noneOf(HorseUpgrade.class);
    public static EnumSet<HorseUpgrade> getUpgrades(ItemStack saddle) {
        if (saddle.isEmpty()) {
            return EMPTY_UPGRADES;
        }

        NBTTagCompound compound = getHorseTweaksUpgrades(saddle, false);
        EnumSet<HorseUpgrade> result = EnumSet.noneOf(HorseUpgrade.class);
        for (HorseUpgrade upgrade : HorseUpgrade.values) {
            if (compound.getBoolean(upgrade.name())) {
                result.add(upgrade);
            }
        }

        return result;
    }

    public static boolean hasUpgrade(AbstractHorse horse, HorseUpgrade upgrade) {
        ItemStack saddle = horse.horseChest.getStackInSlot(0);
        return hasUpgrade(saddle, upgrade);
    }

    public static void damageSaddle(AbstractHorse horse) {
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
