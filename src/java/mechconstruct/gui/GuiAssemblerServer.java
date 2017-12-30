package mechconstruct.gui;

import mechconstruct.MechConstruct;
import mechconstruct.gui.blueprint.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class GuiAssemblerServer {
	public static final ResourceLocation BACKGROUND_SHEET = new ResourceLocation(MechConstruct.MOD_ID, "textures/gui/assembler_background.png");
	public static final ResourceLocation MECH_ELEMENTS = new ResourceLocation(MechConstruct.MOD_ID, "textures/gui/assembler_elements.png");
	public final ResourceLocation customElementSheet;

	public GuiAssemblerServer(ResourceLocation elementSheet) {
		this.customElementSheet = elementSheet;
	}

	public GuiAssemblerServer() {
		this.customElementSheet = null;
	}

	public TextFormatting getPercentageColour(int percentage) {
		if (percentage <= 10) {
			return TextFormatting.RED;
		} else if (percentage >= 75) {
			return TextFormatting.GREEN;
		} else {
			return TextFormatting.YELLOW;
		}
	}

	public int getScaledBurnTime(int scale, int burnTime, int totalBurnTime) {
		return (int) (((float) burnTime / (float) totalBurnTime) * scale);
	}

	public int getPercentage(int max, int current) {
		if (current == 0)
			return 0;
		return (int) ((current * 100.0f) / max);
	}

	public void drawDefaultBackground(MechGui gui, int x, int y, int width, int height) {
	}

	public void drawRect(MechGui gui, int x, int y, int width, int height, int colour) {
	}

	/*
		Taken from Gui
	*/
	public void drawGradientRect(MechGui gui, int x, int y, int width, int height, int startColor, int endColor) {
	}

	public int adjustX(MechGui gui, int x) {
		return 0;
	}

	public int adjustY(MechGui gui, int y) {
		return 0;
	}

	public boolean isInRect(MechGui gui, int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return false;
	}

	public void drawSimpleTooltip(MechGui gui, int mouseX, int mouseY, List<String> lines) {
	}

	public void drawPercentTooltip(MechGui gui, int mouseX, int mouseY, int current, int max, String unitLabel, String percentageLabel) {
	}

	public void drawSimpleTooltip(MechGui gui, int mouseX, int mouseY, String... lines) {
	}

	public void drawPlayerSlots(MechGui gui, int posX, int posY, boolean center) {
	}

	public void drawSlot(MechGui gui, int posX, int posY) {
	}

	public void drawString(MechGui gui, String string, int x, int y, int color) {
	}

	public void drawString(MechGui gui, String string, int x, int y) {
	}

	public void setTextureSheet(ResourceLocation textureLocation) {
	}

	public void drawCenteredString(MechGui gui, String string, int y, int colour) {
	}

	public void drawCenteredString(MechGui gui, String string, int x, int y, int colour) {
	}

	public int getStringWidth(String string) {
		return 0;
	}

	public void drawSprite(MechGui gui, ISprite iSprite, int x, int y) {
	}

	public void drawFluid(MechGui gui, FluidStack fluid, int x, int y, int width, int height, int maxCapacity) {

	}
}
