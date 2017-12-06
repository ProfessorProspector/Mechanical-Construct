package mechconstruct.gui;

import mechconstruct.MechConstruct;
import net.minecraft.util.ResourceLocation;

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

	public void drawDefaultBackground(GuiMachine gui, int x, int y, int width, int height) {

	}

	public void drawRect(GuiMachine gui, int x, int y, int width, int height, int colour) {
	}

	public void drawGradientRect(GuiMachine gui, int x, int y, int width, int height, int startColor, int endColor) {

	}

	public int adjustX(GuiMachine gui, int x) {
		return 0;
	}

	public int adjustY(GuiMachine gui, int y) {
		return 0;
	}

	public boolean isInRect(GuiMachine gui, int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return true;
	}

	public void drawPlayerSlots(GuiMachine gui, int posX, int posY, boolean center) {
	}

	public void drawSlot(GuiMachine gui, int posX, int posY) {
	}

	public void drawString(GuiMachine gui, String string, int x, int y, int color) {
	}

	public void drawString(GuiMachine gui, String string, int x, int y) {
	}

	public void setTextureSheet(ResourceLocation textureLocation) {
	}

	public void drawCenteredString(GuiMachine gui, String string, int y, int colour) {
	}

	public void drawCenteredString(GuiMachine gui, String string, int x, int y, int colour) {
	}

	public int getStringWidth(String string) {
		return 0;
	}

	public void drawSprite(GuiMachine gui, Sprite sprite, int x, int y) {

	}
}
