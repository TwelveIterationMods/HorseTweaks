package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.blay09.mods.horsetweaks.blocks.BlockCrumblingMagma;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FireWalkerHandler {

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Entity entity = event.player.getRidingEntity();
        if (event.phase == TickEvent.Phase.END && entity instanceof AbstractHorse && HorseUpgradeHelper.hasUpgrade((AbstractHorse) entity, HorseUpgrade.FIRE_RESISTANCE)) {
            entity.isImmuneToFire = true;

            solidifyNearby((AbstractHorse) entity, entity.world, entity.getPosition());

            if (entity.ticksExisted % 20 == 0 && entity.world.getBlockState(entity.getPosition().down()).getBlock() == HorseTweaks.blockCrumblingMagma) {
                HorseUpgradeHelper.damageSaddle((AbstractHorse) entity);
            }
        }
    }

    private static void solidifyNearby(EntityLivingBase entity, World world, BlockPos pos) {
        double range = 3;
        BlockPos.MutableBlockPos mutPosAbove = new BlockPos.MutableBlockPos();

        for (BlockPos.MutableBlockPos entry : BlockPos.getAllInBoxMutable(pos.add(-range, -1, -range), pos.add(range, -1, range))) {
            if (entry.distanceSqToCenter(entity.posX, entity.posY, entity.posZ) <= (range * range)) {
                mutPosAbove.setPos(entry.getX(), entry.getY() + 1, entry.getZ());
                IBlockState stateAbove = world.getBlockState(mutPosAbove);
                if (stateAbove.getMaterial() == Material.AIR) {
                    IBlockState state = world.getBlockState(entry);
                    boolean isLava = state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA;
                    if (isLava && world.mayPlace(HorseTweaks.blockCrumblingMagma, entry, false, EnumFacing.DOWN, null)) {
                        boolean isSource = state.getValue(BlockLiquid.LEVEL) == 0;
                        world.setBlockState(entry, HorseTweaks.blockCrumblingMagma.getDefaultState().withProperty(BlockCrumblingMagma.SOURCE, isSource));
                        world.scheduleUpdate(entry.toImmutable(), HorseTweaks.blockCrumblingMagma, MathHelper.getInt(entity.getRNG(), 60, 120));
                    }
                }
            }
        }
    }

}
