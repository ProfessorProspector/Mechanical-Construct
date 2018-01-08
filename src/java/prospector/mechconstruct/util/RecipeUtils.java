package prospector.mechconstruct.util;

import prospector.mechconstruct.api.MechRecipe;
import prospector.mechconstruct.api.MechRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

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
					inputsLeft.removeIf(i -> i instanceof ItemStack && (((ItemStack) i).isItemEqual((ItemStack) input) || (((ItemStack) i).getItem().equals(((ItemStack) input).getItem()) && ((ItemStack) input).getMetadata() == OreDictionary.WILDCARD_VALUE)));
				} else if (input instanceof FluidStack) {
					inputsLeft.removeIf(i -> i instanceof FluidStack && ((FluidStack) i).containsFluid((FluidStack) input));
				}
			}
			if (inputsLeft.isEmpty()) {
				return recipe;
			} else {
				inputsLeft.clear();
				inputsLeft.addAll(inputs);
			}
		}
		return null;
	}
}
