package net.blay09.mods.horsetweaks;

import javax.annotation.Nullable;
import java.util.Locale;

public enum HorseUpgrade {
    SWIMMING,
    EASY_JUMP,
    THORNS,
    FROST_WALKER,
    LEAF_WALKER,
    FEATHER_FALL;

    public static final HorseUpgrade[] values = values();

    @Nullable
    public static HorseUpgrade fromName(String name) {
        try {
            return valueOf(name.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
