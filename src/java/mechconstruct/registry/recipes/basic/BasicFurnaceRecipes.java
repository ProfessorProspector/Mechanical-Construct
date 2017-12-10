package mechconstruct.registry.recipes.basic;

import mechconstruct.api.MechRecipeManager;
import mechconstruct.api.impl.basic.BasicFurnaceRecipe;
import mechconstruct.registry.recipes.RecipeMethods;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BasicFurnaceRecipes extends RecipeMethods {
	public static void register() {
		MechRecipeManager.addRecipe(new BasicFurnaceRecipe("dyeBlue", new ItemStack(Items.CLOCK)));
	}
}
