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
			.addElement(new TextElement("Inventory", 4210752, 8, 83))
			.addSlot(SlotType.NORMAL, 20, 20)
			.addSlot(SlotType.NORMAL, 20, 40);
	}

	@Override
	public void machineTick() {
	}
}
