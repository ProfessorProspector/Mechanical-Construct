package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.util.EnergyUtils;

public class BasicFurnace extends BlockEntityMachine {
	public BasicFurnace() {
		super(2, 1000, EnergyUtils.Bandwidth.BASIC, 2);
	}

	@Override
	public void machineTick() {

	}

	@Override
	public GuiBlueprint getGuiBlueprint() {
		return new GuiBlueprint(this)
			.addSlot(SlotType.NORMAL, 20, 20)
			.addSlot(SlotType.NORMAL, 40, 20);
	}
}
