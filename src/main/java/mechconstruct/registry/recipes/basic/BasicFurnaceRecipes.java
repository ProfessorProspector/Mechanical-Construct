package mechconstruct.registry.recipes.basic;

import mechconstruct.api.MechRecipeManager;
import mechconstruct.api.impl.basic.BasicFurnaceRecipe;
import mechconstruct.registry.recipes.RecipeMethods;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.Map;

public class BasicFurnaceRecipes extends RecipeMethods {
	public static void register() {
		MechRecipeManager.addRecipe(new BasicFurnaceRecipe("dyeBlue", new ItemStack(Items.CLOCK)));
	}

	public static void postInit() {
		Map<ItemStack, ItemStack> furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();
		for (ItemStack input : furnaceRecipes.keySet()) {
			MechRecipeManager.addRecipe(new BasicFurnaceRecipe(input, furnaceRecipes.get(input)));
		}
	}
}
