package prospector.mechconstruct.api.impl.basic;

import prospector.mechconstruct.api.MechRecipe;
import net.minecraft.item.ItemStack;

public class BasicFurnaceRecipe extends MechRecipe {

	public static final int DEFAULT_COST = 20;
	public static final int DEFAULT_TICKS = 80;
	public int cost;
	public int ticks;

	public BasicFurnaceRecipe(ItemStack input, ItemStack output, int cost, int ticks) {
		super("basic_furnace");
		addInput(input);
		addOutput(output);
		this.cost = cost;
		this.ticks = ticks;
	}

	public BasicFurnaceRecipe(ItemStack input, ItemStack output) {
		this(input, output, DEFAULT_COST, DEFAULT_TICKS);
	}

	public BasicFurnaceRecipe(String input, ItemStack output, int cost, int ticks) {
		super("basic_furnace");
		addInput(input);
		addOutput(output);
		this.cost = cost;
		this.ticks = ticks;
	}

	public BasicFurnaceRecipe(String input, ItemStack output) {
		this(input, output, DEFAULT_COST, DEFAULT_TICKS);
	}
}
