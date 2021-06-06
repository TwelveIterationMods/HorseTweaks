package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.blay09.mods.horsetweaks.block.CrumblingMagmaBlock;
import net.blay09.mods.horsetweaks.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID)
public class FireWalkerHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Entity entity = event.player.getRidingEntity();
        if (event.phase == TickEvent.Phase.END && entity instanceof AbstractHorseEntity && HorseUpgradeHelper.hasUpgrade((AbstractHorseEntity) entity, HorseUpgrade.FIRE_RESISTANCE)) {
            entity.isImmuneToFire = true;

            solidifyNearby((AbstractHorseEntity) entity, entity.world, entity.getPosition());

            if (entity.ticksExisted % 20 == 0 && entity.world.getBlockState(entity.getPosition().down()).getBlock() == ModBlocks.crumblingMagma) {
                HorseUpgradeHelper.damageSaddle((AbstractHorseEntity) entity);
            }
        }
    }

    private static void solidifyNearby(LivingEntity entity, World world, BlockPos pos) {
        double range = 3;
        BlockPos.Mutable mutPosAbove = new BlockPos.Mutable();

        for (BlockPos entry : BlockPos.getAllInBoxMutable(pos.add(-range, -1, -range), pos.add(range, -1, range))) {
            if (entry.distanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ(), false) <= (range * range)) {
                mutPosAbove.setPos(entry.getX(), entry.getY() + 1, entry.getZ());
                BlockState stateAbove = world.getBlockState(mutPosAbove);
                if (stateAbove.getMaterial() == Material.AIR) {
                    BlockState state = world.getBlockState(entry);
                    boolean isLava = state.getBlock() == Blocks.LAVA;
                    if (isLava && world.mayPlace(ModBlocks.crumblingMagma, entry, false, Direction.DOWN, null)) {
                        boolean isSource = state.get(BlockLiquid.LEVEL) == 0;
                        world.setBlockState(entry, ModBlocks.crumblingMagma.getDefaultState().with(CrumblingMagmaBlock.SOURCE, isSource));
                        world.scheduleUpdate(entry.toImmutable(), ModBlocks.crumblingMagma, MathHelper.getInt(entity.getRNG(), 60, 120));
                    }
                }
            }
        }
    }

}
