package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.util.EnergyUtils;

public class BasicGrinder extends BlockEntityMachine {
	public BasicGrinder() {
		super(2, 1000, EnergyUtils.Bandwidth.BASIC, 2);
		this.mainBlueprint = new GuiBlueprint(this).addSlot(SlotType.NORMAL, 20, 20).addSlot(SlotType.NORMAL, 20, 40);
	}

	@Override
	public void machineTick() {

	}
}
