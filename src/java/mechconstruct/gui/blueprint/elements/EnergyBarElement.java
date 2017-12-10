package mechconstruct.gui.blueprint.elements;

import mechconstruct.util.EnergyHandler;

public class EnergyBarElement extends ElementBase {
	EnergyHandler handler;

	public EnergyBarElement(EnergyHandler energyHandler, int x, int y) {
		super(x, y);
	}
}