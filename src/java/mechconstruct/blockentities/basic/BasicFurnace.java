package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.elements.ButtonElement;
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
			.addSlot(SlotType.NORMAL, 40, 20)
				.addButton(new ButtonElement("First Slot", 60, 30, 20, 20)
						.setCustomSprite(Sprite.CHARGE_SLOT_ICON, 1, 1)
						.setAction(machine -> System.out.println(machine.getItemInventory().getStackInSlot(0))))
				.addButton(new ButtonElement("Second Slot", 60, 44, 80, 12)
						.setAction(machine -> System.out.println(machine.getItemInventory().getStackInSlot(1))));
	}
}
