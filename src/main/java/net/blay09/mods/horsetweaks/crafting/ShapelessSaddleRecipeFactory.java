package net.blay09.mods.horsetweaks.crafting;

import com.google.gson.JsonObject;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class ShapelessSaddleRecipeFactory implements IRecipeFactory {

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
        String upgradeName = JsonUtils.getString(json, "upgrade");
        HorseUpgrade upgrade = HorseUpgrade.fromName(upgradeName);
        return new ShapelessSaddleRecipe(new ResourceLocation(recipe.getGroup()), recipe.getIngredients(), recipe.getRecipeOutput(), upgrade);
    }

}
