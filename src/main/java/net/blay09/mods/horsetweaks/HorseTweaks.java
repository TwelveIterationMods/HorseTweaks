package net.blay09.mods.horsetweaks;

import net.blay09.mods.horsetweaks.blocks.BlockCrumblingMagma;
import net.blay09.mods.horsetweaks.tweaks.*;
import net.blay09.mods.horsetweaks.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = HorseTweaks.MOD_ID, name = "Horse Tweaks")
@Mod.EventBusSubscriber
public class HorseTweaks {

    public static final String MOD_ID = "horsetweaks";

    private static Logger logger;

    @SidedProxy(serverSide = "net.blay09.mods.horsetweaks.CommonProxy", clientSide = "net.blay09.mods.horsetweaks.client.ClientProxy")
    public static CommonProxy proxy;

    public static CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.SADDLE);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> list) {
            ItemStack saddle = new ItemStack(Items.SADDLE);
            list.add(saddle);

            for (HorseUpgrade upgrade : HorseUpgrade.values) {
                list.add(HorseUpgradeHelper.applyUpgrade(saddle.copy(), upgrade));
            }

            ItemStack megaSaddle = saddle.copy();
            for (HorseUpgrade upgrade : HorseUpgrade.values) {
                HorseUpgradeHelper.applyUpgrade(megaSaddle, upgrade);
            }
            list.add(megaSaddle);
        }
    };

    @GameRegistry.ObjectHolder("crumbling_magma")
    public static Block blockCrumblingMagma;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        MinecraftForge.EVENT_BUS.register(new CreativeInstantTamingHandler());
        MinecraftForge.EVENT_BUS.register(new LeadNoDropHandler());
        MinecraftForge.EVENT_BUS.register(new SaddleRightClickHandler());
        MinecraftForge.EVENT_BUS.register(new UnmountSetHomeHandler());
        MinecraftForge.EVENT_BUS.register(new ThornsHandler());
        MinecraftForge.EVENT_BUS.register(new FrostWalkerHandler());
        MinecraftForge.EVENT_BUS.register(new FeatherFallHandler());
        MinecraftForge.EVENT_BUS.register(new SyncHorseDataHandler());
        MinecraftForge.EVENT_BUS.register(new LeafWalkerHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
        MinecraftForge.EVENT_BUS.register(new RejectPigSaddlesHandler());
        MinecraftForge.EVENT_BUS.register(new FireWalkerHandler());

        proxy.preInit();

        NetworkHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        Items.SADDLE.setMaxDamage(HorseTweaksConfig.saddleDurability);
        Items.SADDLE.setNoRepair();
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(MOD_ID)) {
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockCrumblingMagma().setRegistryName(MOD_ID, "crumbling_magma"));
    }



}
