package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.Sprite;
import mechconstruct.proxy.MechClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class TopEnergyBarElement extends ElementBase {
	public int energy;
	public int capacity;
	public int energyBarWidth = Sprite.TOP_ENERGY_BAR.width;

	public TopEnergyBarElement(int x, int y) {
		super(x, y, Sprite.TOP_ENERGY_BAR_BACKGROUND);
		addHoverAction((element, gui, provider, mouseX, mouseY) -> {
			int percentage = MechClient.GUI_ASSEMBLER.getPercentage(capacity, energy);
			List<String> list = new ArrayList<>();
			TextFormatting powerColour = TextFormatting.GOLD;
			list.add(powerColour + "" + energy + "/" + capacity + " FE");
			list.add(MechClient.GUI_ASSEMBLER.getPercentageColour(percentage) + "" + percentage + "%" + TextFormatting.GRAY + " Charged");
			net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRenderer);
			GlStateManager.disableLighting();
			GlStateManager.color(1, 1, 1, 1);
		});
	}

	public void setEnergyValues(int energy, int capacity) {
		this.energy = energy;
		this.capacity = capacity;
	}

	@Override
	public void draw(MechGui gui) {
		super.draw(gui);
		MechClient.GUI_ASSEMBLER.drawSprite(gui, Sprite.TOP_ENERGY_BAR_BACKGROUND, x, y);
		setEnergyValues(gui.provider.getEnergyInventory().getEnergy(), gui.provider.getEnergyInventory().getCapacity());
		int draw = (int) ((double) energy / (double) capacity * energyBarWidth);
		if (energy > capacity) {
			draw = (int) ((double) capacity / capacity * energyBarWidth);
		}

		MechClient.GUI_ASSEMBLER.drawSprite(gui, new Sprite(Sprite.TOP_ENERGY_BAR.textureLocation, Sprite.TOP_ENERGY_BAR.x, Sprite.TOP_ENERGY_BAR.y, draw, Sprite.TOP_ENERGY_BAR.height), x + 1, y + 1);
	}
}