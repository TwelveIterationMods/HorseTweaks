package net.blay09.mods.horsetweaks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFrostedIce;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrumblingMagma extends BlockMagma {

    public static final PropertyInteger AGE = BlockFrostedIce.AGE;

    public BlockCrumblingMagma() {
        setDefaultState(blockState.getBaseState().withProperty(AGE, 0));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE, MathHelper.clamp(meta, 0, 3));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if ((rand.nextInt(3) == 0 || countNeighbors(world, pos) < 4) && world.getLightFromNeighbors(pos) > 11 - state.getValue(AGE) - state.getLightOpacity()) {
            slightlyMelt(world, pos, state, rand, true);
        } else {
            world.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (block == this) {
            int count = countNeighbors(world, pos);
            if (count < 2) {
                turnIntoLava(world, pos);
            }
        }
    }

    private void turnIntoLava(World world, BlockPos pos) {
        dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
        world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        world.neighborChanged(pos, Blocks.LAVA, pos);
    }

    private int countNeighbors(World world, BlockPos pos) {
        int count = 0;

        for (EnumFacing enumfacing : EnumFacing.values()) {
            if (world.getBlockState(pos.offset(enumfacing)).getBlock() == this) {
                count++;

                if (count >= 4) {
                    return count;
                }
            }
        }

        return count;
    }

    private void slightlyMelt(World world, BlockPos pos, IBlockState state, Random rand, boolean meltNeighbors) {
        int age = state.getValue(AGE);

        if (age < 3) {
            world.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
            world.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        } else {
            turnIntoLava(world, pos);

            if (meltNeighbors) {
                for (EnumFacing facing : EnumFacing.values()) {
                    BlockPos neighbourPos = pos.offset(facing);
                    IBlockState blockState = world.getBlockState(neighbourPos);

                    if (blockState.getBlock() == this) {
                        slightlyMelt(world, neighbourPos, blockState, rand, false);
                    }
                }
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
        return false;
    }

}
