package net.blay09.mods.horsetweaks.crafting;

import net.blay09.mods.horsetweaks.HorseUpgrade;
import net.blay09.mods.horsetweaks.HorseUpgradeHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nullable;

public class ShapelessSaddleRecipe extends ShapelessOreRecipe {

    private HorseUpgrade upgrade;

    public ShapelessSaddleRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result, @Nullable HorseUpgrade upgrade) {
        super(group, input, result);
        this.upgrade = upgrade;
        if (upgrade != null) {
            output = HorseUpgradeHelper.applyUpgrade(output, upgrade);
        }
        isSimple = false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack foundSaddle = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack slotStack = inv.getStackInSlot(i);
            if (!slotStack.isEmpty() && slotStack.getItem() == Items.SADDLE) {
                foundSaddle = slotStack;
            }
        }

        if (foundSaddle == null) {
            return super.getCraftingResult(inv);
        }

        ItemStack result = foundSaddle.copy();
        HorseUpgradeHelper.applyUpgrade(result, upgrade);
        return result;
    }

}
