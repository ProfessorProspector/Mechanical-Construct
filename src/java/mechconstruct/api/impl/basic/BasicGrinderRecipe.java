package mechconstruct.api.impl.basic;

import mechconstruct.api.MechRecipe;
import net.minecraft.item.ItemStack;

public class BasicGrinderRecipe extends MechRecipe {

	public static final int DEFAULT_COST = 20;
	public static final int DEFAULT_TICKS = 80;
	public int cost;
	public int ticks;

	public BasicGrinderRecipe(ItemStack input, ItemStack output, int cost, int ticks) {
		super("basic_grinder");
		addInput(input);
		addOutput(output);
		this.cost = cost;
		this.ticks = ticks;
	}

	public BasicGrinderRecipe(ItemStack input, ItemStack output) {
		this(input, output, DEFAULT_COST, DEFAULT_TICKS);
	}
}
