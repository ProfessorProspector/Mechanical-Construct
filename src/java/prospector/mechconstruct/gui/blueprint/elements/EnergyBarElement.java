package prospector.mechconstruct.gui.blueprint.elements;

import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.gui.MechGui;
import prospector.mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnergyBarElement extends Element {
	public int energy;
	public int capacity;
	public int energyBarHeight = Sprite.ENERGY_BAR.height;

	public EnergyBarElement(int x, int y) {
		super(x, y, Sprite.ENERGY_BAR_BACKGROUND);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawTooltip(MechGui gui, int mouseX, int mouseY) {
		super.drawTooltip(gui, mouseX, mouseY);
		MechConstruct.proxy.getGuiAssembler().drawPercentTooltip(gui, mouseX, mouseY, energy, capacity, I18n.format("gui.mechconstruct.energy_unit"), I18n.format("gui.mechconstruct.charged"));
	}

	public void setEnergyValues(int energy, int capacity) {
		this.energy = energy;
		this.capacity = capacity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(MechGui gui) {
		super.draw(gui);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.ENERGY_BAR_BACKGROUND, x, y);
		setEnergyValues(gui.provider.getEnergyInventory().getEnergy(), gui.provider.getEnergyInventory().getCapacity());
		int draw = (int) ((double) energy / (double) capacity * energyBarHeight);
		if (energy > capacity) {
			draw = (int) ((double) capacity / capacity * energyBarHeight);
		}

		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, new Sprite(Sprite.ENERGY_BAR.textureLocation, Sprite.ENERGY_BAR.x, Sprite.ENERGY_BAR.y + energyBarHeight - draw, Sprite.ENERGY_BAR.width, draw), x + 1, y + 1 + energyBarHeight - draw);
	}
}