package prospector.mechconstruct.api;

import net.minecraft.item.ItemStack;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class MechRecipe {
	public final String recipeType;
	private final ArrayList<Object> inputs;
	private final ArrayList<Object> outputs;

	public MechRecipe(String recipeType) {
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		this.recipeType = recipeType;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public ArrayList<Object> getInputs() {
		return inputs;
	}

	public ArrayList<Object> getOutputs() {
		return outputs;
	}

	public void addInput(Object input) {
		if (input == null) {
			throw new InvalidParameterException("Input invalid! Object is null");
		}
		if (input instanceof ItemStack) {
			if (((ItemStack) input).isEmpty()) {
				throw new InvalidParameterException("Input invalid! Output stack is empty!");
			}
		}
		inputs.add(input);
	}

	public void addOutput(Object output) {
		if (output == null) {
			throw new InvalidParameterException("Output invalid! Object is null");
		}
		if (output instanceof ItemStack) {
			if (((ItemStack) output).isEmpty()) {
				throw new InvalidParameterException("Output invalid! Output stack is empty!");
			}
		}
		outputs.add(output);
	}
}
