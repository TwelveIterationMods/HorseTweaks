package net.blay09.mods.horsetweaks.block;

import net.blay09.mods.horsetweaks.HorseTweaks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
    public static Block crumblingMagma;

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                crumblingMagma = new CrumblingMagmaBlock().setRegistryName("crumbling_magma")
        );
    }

    public static void registerBlockItems(IForgeRegistry<Item> registry) {
        registry.registerAll(
                new BlockItem(crumblingMagma, new Item.Properties().group(HorseTweaks.itemGroup)).setRegistryName("crumbling_magma")
        );
    }
}
