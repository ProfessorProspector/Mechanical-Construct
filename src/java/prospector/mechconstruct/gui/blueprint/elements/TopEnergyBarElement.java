package prospector.mechconstruct.gui.blueprint.elements;

import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.gui.MechGui;
import prospector.mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TopEnergyBarElement extends Element {
	public int energy;
	public int capacity;
	public int energyBarWidth = Sprite.TOP_ENERGY_BAR.width;

	public TopEnergyBarElement(int x, int y) {
		super(x, y, Sprite.TOP_ENERGY_BAR_BACKGROUND);
	}

	public void setEnergyValues(int energy, int capacity) {
		this.energy = energy;
		this.capacity = capacity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(MechGui gui) {
		super.draw(gui);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.TOP_ENERGY_BAR_BACKGROUND, x, y);
		setEnergyValues(gui.provider.getEnergyInventory().getEnergy(), gui.provider.getEnergyInventory().getCapacity());
		int draw = (int) ((double) energy / (double) capacity * energyBarWidth);
		if (energy > capacity) {
			draw = (int) ((double) capacity / capacity * energyBarWidth);
		}

		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, new Sprite(Sprite.TOP_ENERGY_BAR.textureLocation, Sprite.TOP_ENERGY_BAR.x, Sprite.TOP_ENERGY_BAR.y, draw, Sprite.TOP_ENERGY_BAR.height), x + 1, y + 1);
	}

	@SideOnly(Side.CLIENT)
	public void drawTooltip(MechGui gui, int mouseX, int mouseY) {
		super.drawTooltip(gui, mouseX, mouseY);
		MechConstruct.proxy.getGuiAssembler().drawPercentTooltip(gui, mouseX, mouseY, energy, capacity, I18n.format("gui.mechconstruct.energy_unit"), I18n.format("gui.mechconstruct.charged"));
	}
}