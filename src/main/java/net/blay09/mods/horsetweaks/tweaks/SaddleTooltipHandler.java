package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Locale;

public class SaddleTooltipHandler {

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == Items.SADDLE) {
            List<HorseUpgrade> upgradeList = HorseUpgradeHelper.getUpgrades(event.getItemStack());

            // Rename the vanilla saddle item in this tooltip if it's been upgraded
            if (!upgradeList.isEmpty() && event.getToolTip().size() > 0) {
                event.getToolTip().set(0, TextFormatting.AQUA + I18n.format("horsetweaks:item.upgraded_saddle"));
            }

            // Remove the fake enchantment tooltip label
            // event.getToolTip().removeIf(p -> p.contains("enchantment.level.0"));

            // List the upgrades in the tooltip
            for (HorseUpgrade upgrade : upgradeList) {
                event.getToolTip().add(I18n.format("horsetweaks:upgrade." + upgrade.name().toLowerCase(Locale.ENGLISH)));
            }
        }
    }

}
