package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.blay09.mods.horsetweaks.mixin.AbstractHorseEntityAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class LeafWalkerHandler {

    @SubscribeEvent
    public static void getCollisionBoxes(World world, Entity entity, List<AxisAlignedBB> collisions) {
        if (!entity.isBeingRidden() || !(entity instanceof AbstractHorseEntity)) {
            return;
        }

        AbstractHorseEntity horse = (AbstractHorseEntity) entity;
        ItemStack saddle = ((AbstractHorseEntityAccessor) horse).getHorseChest().getStackInSlot(0);
        if (!HorseTweaksConfig.COMMON.leafWalkerByDefault.get() && !HorseUpgradeHelper.hasUpgrade(saddle, HorseUpgrade.LEAF_WALKER)) {
            return;
        }

        boolean foundOne = false;
        for (int i = collisions.size() - 1; i >= 0; i--) {
            AxisAlignedBB aabb = collisions.get(i);
            BlockPos pos = new BlockPos(aabb.minX + (aabb.maxX - aabb.minX) * 0.5f, aabb.minY + (aabb.maxY - aabb.minY) * 0.5f, aabb.minZ + (aabb.maxZ - aabb.minZ) * 0.5f);
            BlockState state = world.getBlockState(pos);
            if (BlockTags.LEAVES.contains(state.getBlock()) && entity.getPosY() < aabb.maxY) {
                collisions.remove(i);
                foundOne = true;
            }
        }
        if (foundOne) {
            if (horse.ticksExisted % 30 == 0) {
                HorseUpgradeHelper.damageSaddle(horse);
            }
        }
    }

}
