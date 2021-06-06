package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.mixin.AbstractHorseEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.EnumSet;

public class HorseUpgradeHelper {

    private static final String HORSE_TWEAKS_UPGRADES_TAG = "HorseTweaksUpgrades";

    private static CompoundNBT getHorseTweaksUpgrades(ItemStack saddle, boolean createIfNecessary) {
        CompoundNBT compound = saddle.getTag();
        if (compound == null) {
            if (createIfNecessary) {
                compound = new CompoundNBT();
                saddle.setTag(compound);
            } else {
                return new CompoundNBT();
            }
        }
        CompoundNBT upgrades = compound.getCompound(HORSE_TWEAKS_UPGRADES_TAG);
        if (createIfNecessary) {
            compound.put(HORSE_TWEAKS_UPGRADES_TAG, upgrades);
        }
        return upgrades;
    }

    public static boolean hasUpgrade(ItemStack saddle, HorseUpgrade upgrade) {
        CompoundNBT upgrades = getHorseTweaksUpgrades(saddle, false);
        return upgrades.getBoolean(upgrade.name());
    }

    public static ItemStack applyUpgrade(ItemStack saddle, HorseUpgrade upgrade) {
        CompoundNBT upgrades = getHorseTweaksUpgrades(saddle, true);
        upgrades.putBoolean(upgrade.name(), true);
        return saddle;
    }

    private static final EnumSet<HorseUpgrade> EMPTY_UPGRADES = EnumSet.noneOf(HorseUpgrade.class);
    public static EnumSet<HorseUpgrade> getUpgrades(ItemStack saddle) {
        if (saddle.isEmpty()) {
            return EMPTY_UPGRADES;
        }

        CompoundNBT compound = getHorseTweaksUpgrades(saddle, false);
        EnumSet<HorseUpgrade> result = EnumSet.noneOf(HorseUpgrade.class);
        for (HorseUpgrade upgrade : HorseUpgrade.values) {
            if (compound.getBoolean(upgrade.name())) {
                result.add(upgrade);
            }
        }

        return result;
    }

    public static boolean hasUpgrade(AbstractHorseEntity horse, HorseUpgrade upgrade) {
        Inventory horseChest = ((AbstractHorseEntityAccessor) horse).getHorseChest();
        ItemStack saddle = horseChest.getStackInSlot(0);
        return hasUpgrade(saddle, upgrade);
    }

    public static void damageSaddle(AbstractHorseEntity horse) {
        if (HorseTweaksConfig.saddleDurability == 0) {
            return;
        }

        Inventory horseChest = ((AbstractHorseEntityAccessor) horse).getHorseChest();
        ItemStack saddle = horseChest.getStackInSlot(0);
        Entity passenger = horse.getControllingPassenger();
        saddle.damageItem(1, passenger instanceof PlayerEntity ? (PlayerEntity) passenger : horse, it -> {});
        if (saddle.getDamage() >= saddle.getMaxDamage()) {
            horseChest.markDirty();
        }
    }

}
