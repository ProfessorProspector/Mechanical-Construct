package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.Sprite;
import mechconstruct.proxy.MechClient;

public class EnergyBarElement extends ElementBase {
	int energy;
	int capacity;

	public EnergyBarElement(int x, int y) {
		super(x, y);
	}

	public void setEnergyValues(int energy, int capacity) {
		this.energy = energy;
		this.capacity = capacity;
	}

	@Override
	public void draw(MechGui gui) {
		super.draw(gui);
		MechClient.GUI_ASSEMBLER.drawSprite(gui, Sprite.ENERGY_BAR_BACKGROUND, x, y);

		MechClient.GUI_ASSEMBLER.drawSprite(gui, new Sprite(Sprite.ENERGY_BAR.textureLocation, Sprite.ENERGY_BAR.x, texY, Sprite.ENERGY_BAR.width, texHeight), x + 1, y + 1 + dd);
	}
}