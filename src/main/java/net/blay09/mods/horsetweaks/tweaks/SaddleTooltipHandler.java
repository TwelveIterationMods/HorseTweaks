package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.EnumSet;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = HorseTweaks.MOD_ID, value = Dist.CLIENT)
public class SaddleTooltipHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == Items.SADDLE) {
            EnumSet<HorseUpgrade> upgradeList = HorseUpgradeHelper.getUpgrades(event.getItemStack());

            // Rename the vanilla saddle item in this tooltip if it's been upgraded
            if (!upgradeList.isEmpty() && event.getToolTip().size() > 0) {
                event.getToolTip().set(0, TextFormatting.AQUA + I18n.format("horsetweaks:item.upgraded_saddle"));
            }

            // List the upgrades in the tooltip
            for (HorseUpgrade upgrade : upgradeList) {
                event.getToolTip().add(I18n.format("horsetweaks:upgrade." + upgrade.name().toLowerCase(Locale.ENGLISH)));
            }
        }
    }

}
