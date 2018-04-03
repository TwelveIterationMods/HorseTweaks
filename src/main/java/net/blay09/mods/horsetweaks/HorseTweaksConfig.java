package net.blay09.mods.horsetweaks;

import net.minecraftforge.common.config.Config;

@Config(modid = HorseTweaks.MOD_ID)
public class HorseTweaksConfig {

    @Config.Name("Return Leads into Inventory")
    @Config.Comment("This will make leads no longer drop to the ground when detached, instead they will go straight into the player's inventory.")
    public static boolean returnLeadsIntoInventory = true;

    @Config.Name("Apply Saddles on Right-Click")
    @Config.Comment("This will make saddles be applied on right-click instead of opening the inventory for tedious manual insertion.")
    public static boolean addSaddleOnRightClick = true;

    @Config.Name("Set Home on Dismount")
    @Config.Comment("This sets the home position for tamed horses on dismount, preventing them to wander off too far from where you've left them.")
    public static boolean setHomeOnDismount = true;

    @Config.Name("Instantly Tame in Creative Mode")
    @Config.Comment("This makes taming of horses instant while in creative mode, for simpler testing.")
    public static boolean instantTameInCreative = true;

    @Config.Name("Enable Easy Jump by Default")
    @Config.Comment("If enabled, easy jump will be available without requiring the saddle upgrade. Easy Jump disables the charge-up and grants them a fixed jump height instead.")
    public static boolean easyJumpByDefault = false;

    @Config.Name("Enable Leaf Walker by Default")
    @Config.Comment("If enabled, horses can walk through leaves without requiring the saddle upgrade. Walking on top of leaves is still possible.")
    public static boolean leafWalkerByDefault = false;

    @Config.Name("Enable Swimming by Default")
    @Config.Comment("If enabled, horses will be able to swim without requiring the saddle upgrade. Horses are much slower while swimming.")
    public static boolean swimmingByDefault = false;

    @Config.Name("Enable Feather Fall by Default")
    @Config.Comment("If enabled, horses not take fall damage (nor will their rider) without requiring the saddle upgrade.")
    public static boolean featherFallByDefault = false;

    @Config.Name("Saddle Durability")
    @Config.Comment("Amount of durability points for a saddle. Saddles receive damage every time their special abilities are used. Set to 0 for infinite durability.")
    public static int saddleDurability = 1000;

    @Config.Name("Render Upgrades on Horse")
    @Config.Comment("Set to false if you don't want the saddle upgrades to be rendered on the horse model.")
    public static boolean renderUpgradesOnHorse = true;
}
