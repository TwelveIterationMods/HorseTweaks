package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.block.ModBlocks;
import net.blay09.mods.horsetweaks.item.ModItems;
import net.blay09.mods.horsetweaks.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HorseTweaks.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseTweaks {

    public static final String MOD_ID = "horsetweaks";

    public static Logger logger = LogManager.getLogger();

    public static final ItemGroup itemGroup = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.SADDLE);
        }

        @Override
        public void fill(NonNullList<ItemStack> items) {
            ItemStack saddle = new ItemStack(Items.SADDLE);
            items.add(saddle);

            for (HorseUpgrade upgrade : HorseUpgrade.values) {
                items.add(HorseUpgradeHelper.applyUpgrade(saddle.copy(), upgrade));
            }

            ItemStack megaSaddle = saddle.copy();
            for (HorseUpgrade upgrade : HorseUpgrade.values) {
                HorseUpgradeHelper.applyUpgrade(megaSaddle, upgrade);
            }
            items.add(megaSaddle);
        }
    };

    public HorseTweaks() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HorseTweaksConfig.commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HorseTweaksConfig.clientSpec);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
        ModBlocks.registerBlockItems(event.getRegistry());
    }

}
