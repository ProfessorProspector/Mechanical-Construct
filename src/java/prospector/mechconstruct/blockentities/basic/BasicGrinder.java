package prospector.mechconstruct.blockentities.basic;

import prospector.mechconstruct.blockentities.BlockEntityMachine;
import prospector.mechconstruct.gui.SlotType;
import prospector.mechconstruct.gui.blueprint.GuiBlueprint;
import prospector.mechconstruct.gui.blueprint.elements.TextElement;
import prospector.mechconstruct.util.EnergyUtils;

public class BasicGrinder extends BlockEntityMachine {
	public BasicGrinder() {
		super(2, 1000, EnergyUtils.Bandwidth.BASIC, 12);
		this.mainBlueprint = new GuiBlueprint(this)
			.setPlayerInvPos(7, 93)
			.addElement(new TextElement("container.inventory", true, 0x404040, 8, 83))
			.addMachineSlot(SlotType.NORMAL, 20, 20)
			.addMachineSlot(SlotType.NORMAL, 20, 30);
	}

	@Override
	public void machineTick() {
	}
}
