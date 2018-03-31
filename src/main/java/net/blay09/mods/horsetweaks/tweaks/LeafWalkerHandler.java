package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaksConfig;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class LeafWalkerHandler {

    @SubscribeEvent
    public void getCollisionBoxes(GetCollisionBoxesEvent event) {
        if (event.getEntity() == null || !event.getEntity().isBeingRidden() || !(event.getEntity() instanceof EntityHorse)) {
            return;
        }

        EntityHorse horse = (EntityHorse) event.getEntity();
        ItemStack saddle = horse.horseChest.getStackInSlot(0);
        if (!HorseTweaksConfig.leafWalkerByDefault && !HorseUpgradeHelper.hasUpgrade(saddle, HorseUpgrade.LEAF_WALKER)) {
            return;
        }

        List<AxisAlignedBB> collisions = event.getCollisionBoxesList();
        boolean foundOne = false;
        for (int i = collisions.size() - 1; i >= 0; i--) {
            AxisAlignedBB aabb = collisions.get(i);
            BlockPos pos = new BlockPos(aabb.getCenter());
            IBlockState state = event.getWorld().getBlockState(pos);
            if (state.getBlock().isLeaves(state, event.getWorld(), pos) && event.getEntity().posY < aabb.maxY) {
                event.getCollisionBoxesList().remove(i);
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
