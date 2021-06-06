package net.blay09.mods.horsetweaks.tweaks;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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

            // List the upgrades in the tooltip
            for (HorseUpgrade upgrade : upgradeList) {
                event.getToolTip().add(new TranslationTextComponent("horsetweaks.upgrade." + upgrade.name().toLowerCase(Locale.ENGLISH)));
            }
        }
    }

}
