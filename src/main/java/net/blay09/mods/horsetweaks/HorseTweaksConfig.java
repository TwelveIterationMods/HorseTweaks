package net.blay09.mods.horsetweaks;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class HorseTweaksConfig {

    public static class Common {
        public final ForgeConfigSpec.BooleanValue returnLeadsIntoInventory;
        public final ForgeConfigSpec.BooleanValue addSaddleOnRightClick;
        public final ForgeConfigSpec.BooleanValue setHomeOnDismount;
        public final ForgeConfigSpec.BooleanValue instantTameInCreative;
        public final ForgeConfigSpec.BooleanValue easyJumpByDefault;
        public final ForgeConfigSpec.BooleanValue leafWalkerByDefault;
        public final ForgeConfigSpec.BooleanValue swimmingByDefault;
        public final ForgeConfigSpec.BooleanValue featherFallByDefault;
        public final ForgeConfigSpec.IntValue saddleDurability;

        Common(ForgeConfigSpec.Builder builder) {

            builder.push("general");

            saddleDurability = builder
                    .comment("Amount of durability points for a saddle. Saddles receive damage every time their special abilities are used. Set to 0 for infinite durability.")
                    .translation("config.horsetweaks.saddleDurability")
                    .defineInRange("saddleDurability", 1000, 0, Integer.MAX_VALUE);

            builder.pop().push("tweaks");

            returnLeadsIntoInventory = builder
                    .comment("This will make leads no longer drop to the ground when detached, instead they will go straight into the player's inventory.")
                    .translation("config.horsetweaks.returnLeadsIntoInventory")
                    .define("returnLeadsIntoInventory", true);

            addSaddleOnRightClick = builder
                    .comment("This will make saddles be applied on right-click instead of opening the inventory for tedious manual insertion.")
                    .translation("config.horsetweaks.addSaddleOnRightClick")
                    .define("addSaddleOnRightClick", true);

            setHomeOnDismount = builder
                    .comment("This sets the home position for tamed horses on dismount, preventing them to wander off too far from where you've left them.")
                    .translation("config.horsetweaks.setHomeOnDismount")
                    .define("setHomeOnDismount", true);

            instantTameInCreative = builder
                    .comment("This makes taming of horses instant while in creative mode, for simpler testing.")
                    .translation("config.horsetweaks.instantTameInCreative")
                    .define("instantTameInCreative", true);

            easyJumpByDefault = builder
                    .comment("If enabled, easy jump will be available without requiring the saddle upgrade. Easy Jump disables the charge-up and grants them a fixed jump height instead.")
                    .translation("config.horsetweaks.easyJumpByDefault")
                    .define("easyJumpByDefault", true);

            leafWalkerByDefault = builder
                    .comment("If enabled, horses can walk through leaves without requiring the saddle upgrade. Walking on top of leaves is still possible.")
                    .translation("config.horsetweaks.leafWalkerByDefault")
                    .define("leafWalkerByDefault", true);

            swimmingByDefault = builder
                    .comment("If enabled, horses will be able to swim without requiring the saddle upgrade. Horses are much slower while swimming.")
                    .translation("config.horsetweaks.swimmingByDefault")
                    .define("swimmingByDefault", true);

            featherFallByDefault = builder
                    .comment("If enabled, horses not take fall damage (nor will their rider) without requiring the saddle upgrade.")
                    .translation("config.horsetweaks.featherFallByDefault")
                    .define("featherFallByDefault", true);
        }
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue renderUpgradesOnHorse;

        Client(ForgeConfigSpec.Builder builder) {
            builder.push("rendering");

            renderUpgradesOnHorse = builder
                    .comment("Set to false if you don't want the saddle upgrades to be rendered on the horse model.")
                    .translation("config.horsetweaks.renderUpgradesOnHorse")
                    .define("renderUpgradesOnHorse", true);
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

}
