package net.blay09.mods.horsetweaks;

import com.google.common.collect.ImmutableList;
import net.blay09.mods.horsetweaks.client.SaddleItemOverrides;
import net.blay09.mods.horsetweaks.tweaks.*;
import net.blay09.mods.horsetweaks.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ItemLayerModel;
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
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Locale;
import java.util.function.Function;

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
        }
    };

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
    public static void onStitch(TextureStitchEvent.Pre event) {
        for (HorseUpgrade upgrade : HorseUpgrade.values) {
            event.getMap().registerSprite(new ResourceLocation(MOD_ID, "items/" + upgrade.name().toLowerCase(Locale.ENGLISH)));
        }
    }

    @SubscribeEvent
    public static void bakeModels(ModelBakeEvent event) {
        ItemLayerModel model = new ItemLayerModel(ImmutableList.of(new ResourceLocation("minecraft", "items/saddle")), new SaddleItemOverrides(Collections.emptyList()));
        Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        IBakedModel bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
        event.getModelRegistry().putObject(new ModelResourceLocation(new ResourceLocation(MOD_ID, "modded_saddle"), "inventory"), bakedModel);
    }

}
