package net.blay09.mods.horsetweaks.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class CrumblingMagmaBlock extends MagmaBlock {

    public static final IntegerProperty AGE = FrostedIceBlock.AGE;
    public static final BooleanProperty SOURCE = BooleanProperty.create("source");

    public CrumblingMagmaBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK)
                .setRequiresTool()
                .setLightLevel(it -> 3)
                .tickRandomly()
                .hardnessAndResistance(0.5F)
                .setAllowsSpawn((state, reader, pos, entity) -> entity.isImmuneToFire())
                .setNeedsPostProcessing((state, reader, pos) -> true)
                .setEmmisiveRendering((state, reader, pos) -> true));
        setDefaultState(getDefaultState()
                .with(AGE, 0)
                .with(SOURCE, false));
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        if ((rand.nextInt(3) == 0 || countNeighbors(world, pos) < 4) && world.getLightFromNeighbors(pos) > 11 - state.get(AGE) - state.getLightOpacity()) {
            slightlyMelt(world, pos, state, rand, true);
        } else {
            world.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (block == this) {
            int count = countNeighbors(world, pos);
            if (count < 2) {
                turnIntoLava(world, pos);
            }
        }
    }

    private void turnIntoLava(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        dropBlockAsItem(world, pos, state, 0);
        boolean isSource = state.get(SOURCE);
        world.setBlockState(pos, isSource ? Blocks.LAVA.getDefaultState() : Blocks.LAVA.getDefaultState().with(BlockLiquid.LEVEL, 1));
        world.neighborChanged(pos, Blocks.LAVA, pos);
    }

    private int countNeighbors(World world, BlockPos pos) {
        int count = 0;

        for (Direction enumfacing : Direction.values()) {
            if (world.getBlockState(pos.offset(enumfacing)).getBlock() == this) {
                count++;

                if (count >= 4) {
                    return count;
                }
            }
        }

        return count;
    }

    private void slightlyMelt(World world, BlockPos pos, BlockState state, Random rand, boolean meltNeighbors) {
        int age = state.get(AGE);

        if (age < 3) {
            world.setBlockState(pos, state.with(AGE, age + 1), 2);
            world.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        } else {
            turnIntoLava(world, pos);

            if (meltNeighbors) {
                for (Direction facing : Direction.values()) {
                    BlockPos neighbourPos = pos.offset(facing);
                    BlockState blockState = world.getBlockState(neighbourPos);

                    if (blockState.getBlock() == this) {
                        slightlyMelt(world, neighbourPos, blockState, rand, false);
                    }
                }
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, SOURCE);
    }

}
