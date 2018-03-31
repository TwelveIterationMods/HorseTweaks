package net.blay09.mods.horsetweaks.crafting;

import com.google.gson.JsonObject;
import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nullable;

public class ShapelessSaddleRecipeFactory implements IRecipeFactory {

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
        String upgradeName = JsonUtils.getString(json, "upgrade");
        HorseUpgrade upgrade = HorseUpgrade.fromName(upgradeName);
        return new ShapelessSaddleRecipe(new ResourceLocation(recipe.getGroup()), recipe.getIngredients(), recipe.getRecipeOutput(), upgrade);
    }

}
