package mechconstruct.blockentities.basic;

import mechconstruct.api.MechRecipe;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.elements.TankElement;
import mechconstruct.gui.blueprint.elements.TextElement;
import mechconstruct.util.EnergyUtils;
import mechconstruct.util.Tank;
import mechconstruct.util.slotconfig.SlotConfig;
import mechconstruct.util.slotconfig.SlotSideMap;
import net.minecraftforge.fluids.FluidRegistry;

public class BasicBoiler extends BlockEntityMachine {
	public MechRecipe currentRecipe;

	public BasicBoiler() {
		super(1, 1000, EnergyUtils.Bandwidth.BASIC, 2, new Tank("water_input", 4000), new Tank("steam_output", 4000), new Tank("flue_output", 1000));
		this.mainBlueprint = new GuiBlueprint(this)
			.setPlayerInvPos(7, 93)
			.addElement(new TextElement("container.inventory", true, 4210752, 8, 83))
			.addMachineSlot(SlotType.NORMAL, 40, 30, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.INPUT, SlotConfig.INPUT, SlotConfig.OUTPUT))
			.addElement(new TankElement(10, 15, "water_input"))
			.addElement(new TankElement(80, 15, "steam_output"))
			.addElement(new TankElement(110, 15, "flue_output"));
	}

	@Override
	public void machineTick() {
		if (!world.isRemote) {
			setFluid("water_input", FluidRegistry.WATER, 1000);
		}
	}
}
