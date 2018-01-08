package mechconstruct.blockentities.basic;

import mechconstruct.api.MechRecipe;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.elements.TankElement;
import mechconstruct.gui.blueprint.elements.TextElement;
import mechconstruct.util.Tank;
import mechconstruct.util.slotconfig.SlotConfig;
import mechconstruct.util.slotconfig.SlotSideMap;

public class BasicBoiler extends BlockEntityMachine {
	public static final String
		WATER_INPUT = "water_input",
		STEAM_OUTPUT = "steam_output",
		FLUE_OUTPUT = "flue_output";
	public MechRecipe currentRecipe;

	public BasicBoiler() {
		super(3, 2,
			new Tank("water_input", 4000).setDrainable(false),
			new Tank("steam_output", 4000).setFillable(false),
			new Tank("flue_output", 1000).setFillable(false));
		getFluidInventory().setTiles(this);
		this.mainBlueprint = new GuiBlueprint(this)
			.setPlayerInvPos(7, 93)
			.addElement(new TextElement("container.inventory", true, 0x404040, 8, 83))
			.addMachineSlot(SlotType.NORMAL, 9, 23, (slot, stack) -> {
				// If filled fluid container that can be emptied
				return true;
			})
			.addMachineSlot(SlotType.NORMAL, 9, 57, (slot, stack) -> false)
			.addElement(new TankElement(29, 21, WATER_INPUT))
			.addMachineSlot(SlotType.NORMAL, 78, 47, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.INPUT, SlotConfig.INPUT, SlotConfig.OUTPUT), (slot, stack) -> {
				// If is a valid fuel
				return true;
			})
			.addElement(new TankElement(123, 21, STEAM_OUTPUT))
			.addElement(new TankElement(147, 21, FLUE_OUTPUT));
	}

	@Override
	public void machineTick() {
		if (!world.isRemote) {

		}
	}
}
