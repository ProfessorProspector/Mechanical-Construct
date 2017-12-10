package mechconstruct.util;

import mechconstruct.api.MechRecipe;
import mechconstruct.api.MechRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class RecipeUtils {
	public static MechRecipe getValidRecipe(String recipeType, ArrayList<Object> inputs) {
		ArrayList<Object> inputsLeft = new ArrayList<>();
		inputsLeft.addAll(inputs);
		for (MechRecipe recipe : MechRecipeManager.getRecipesOfType(recipeType)) {
			for (Object input : recipe.getInputs()) {
				if (input instanceof String) {
					inputsLeft.removeIf(i -> i instanceof ItemStack && OreDictUtils.isOre((ItemStack) i, (String) input));
				} else if (input instanceof ItemStack) {
					inputsLeft.removeIf(i -> i instanceof ItemStack && ((ItemStack) i).isItemEqual((ItemStack) i));
				} else if (input instanceof FluidStack) {
					inputsLeft.removeIf(i -> i instanceof FluidStack && ((FluidStack) i).containsFluid((FluidStack) input));
				}
			}
			if (inputsLeft.isEmpty()) {
				return recipe;
			}
		}
		return null;
	}
}
