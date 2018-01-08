package mechconstruct.blockentities.basic;

import mechconstruct.api.MechRecipe;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.elements.TextElement;
import mechconstruct.util.EnergyUtils;
import mechconstruct.util.RecipeUtils;
import mechconstruct.util.slotconfig.SlotConfig;
import mechconstruct.util.slotconfig.SlotSideMap;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class BasicFurnace extends BlockEntityMachine {
	public MechRecipe currentRecipe;

	public BasicFurnace() {
		super(2, 1000, EnergyUtils.Bandwidth.BASIC, 2);
		this.mainBlueprint = new GuiBlueprint(this)
			.setPlayerInvPos(7, 93)
			.addElement(new TextElement("container.inventory", true, 0x404040, 8, 83))
			.addMachineSlot(SlotType.NORMAL, 20, 20, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.INPUT, SlotConfig.INPUT, SlotConfig.OUTPUT))
			.addMachineSlot(SlotType.NORMAL, 40, 20, new SlotSideMap(SlotConfig.OUTPUT, SlotConfig.INPUT, SlotConfig.NONE, SlotConfig.INPUT, SlotConfig.INPUT, SlotConfig.INPUT));
	}

	@Override
	public void machineTick() {
		if (!world.isRemote) {
			ArrayList<Object> inputs = new ArrayList<>();
			inputs.add(itemInventory.getStackInSlot(0));
			currentRecipe = RecipeUtils.getValidRecipe("basic_furnace", inputs);
			if (currentRecipe != null) {
				itemInventory.extractItem(0, 1, false);
				itemInventory.insertItem(1, ((ItemStack) currentRecipe.getOutputs().get(0)).copy(), false);
			}
		}
	}
}
