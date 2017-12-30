package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.elements.TextElement;
import mechconstruct.util.EnergyUtils;

public class BasicGrinder extends BlockEntityMachine {
	public BasicGrinder() {
		super(2, 1000, EnergyUtils.Bandwidth.BASIC, 12);
		this.mainBlueprint = new GuiBlueprint(this)
			.setPlayerInvPos(7, 93)
			.addElement(new TextElement("container.inventory", true, 4210752, 8, 83))
			.addMachineSlot(SlotType.NORMAL, 20, 20)
			.addMachineSlot(SlotType.NORMAL, 20, 30);
	}

	@Override
	public void machineTick() {
	}
}
