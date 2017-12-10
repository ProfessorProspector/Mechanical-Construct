package mechconstruct.api;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

public class MechRecipeManager {
	public static final ArrayList<MechRecipe> recipes = new ArrayList<>();
	public static final ArrayList<String> recipeTypes = new ArrayList<>();

	public static List<MechRecipe> getRecipesOfType(String recipeType) {
		List<MechRecipe> machineRecipes = new ArrayList<>();
		for (MechRecipe recipe : recipes) {
			if (recipe.getRecipeType().equals(recipeType)) {
				machineRecipes.add(recipe);
			}
		}
		return machineRecipes;
	}

	public static void addRecipe(MechRecipe recipe) {
		if (recipe == null) {
			return;
		}
		if (recipes.contains(recipe)) {
			return;
		}
		for (Object input : recipe.getInputs()) {
			Validate.notNull(input);
			if (input instanceof ItemStack) {
				Validate.notNull(((ItemStack) input).getItem());
			}
		}
		for (Object output : recipe.getInputs()) {
			Validate.notNull(output);
			if (output instanceof ItemStack) {
				Validate.notNull(((ItemStack) output).getItem());
			}
		}
		if (!recipeTypes.contains(recipe.getRecipeType())) {
			recipeTypes.add(recipe.getRecipeType());
		}
		recipes.add(recipe);

	}
}
