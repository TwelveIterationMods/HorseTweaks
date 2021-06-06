package net.blay09.mods.horsetweaks.item;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
    public static Item upgradedSaddle;

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                upgradedSaddle = new UpgradedSaddleItem().setRegistryName(UpgradedSaddleItem.registryName)
        );
    }
}
