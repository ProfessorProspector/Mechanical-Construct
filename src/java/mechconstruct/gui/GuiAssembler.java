package mechconstruct.gui;

import mechconstruct.MechConstruct;
import mechconstruct.gui.blueprint.ISprite;
import mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAssembler {
	public static final ResourceLocation BACKGROUND_SHEET = new ResourceLocation(MechConstruct.MOD_ID, "textures/gui/assembler_background.png");
	public static final ResourceLocation MECH_ELEMENTS = new ResourceLocation(MechConstruct.MOD_ID, "textures/gui/assembler_elements.png");
	public final ResourceLocation customElementSheet;

	public GuiAssembler(ResourceLocation elementSheet) {
		this.customElementSheet = elementSheet;
	}

	public GuiAssembler() {
		this.customElementSheet = null;
	}

	public void drawDefaultBackground(MechGui gui, int x, int y, int width, int height) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);
		setTextureSheet(BACKGROUND_SHEET);
		GlStateManager.disableLighting();
		gui.drawTexturedModalRect(x, y, 0, 0, width / 2, height / 2);
		gui.drawTexturedModalRect(x + width / 2, y, 256 - width / 2, 0, width / 2, height / 2);
		gui.drawTexturedModalRect(x, y + height / 2, 0, 256 - height / 2, width / 2, height / 2);
		gui.drawTexturedModalRect(x + width / 2, y + height / 2, 256 - width / 2, 256 - height / 2, width / 2, height / 2);
	}

	public void drawRect(MechGui gui, int x, int y, int width, int height, int colour) {
		drawGradientRect(gui, x, y, width, height, colour, colour);
	}

	/*
		Taken from Gui
	*/
	public void drawGradientRect(MechGui gui, int x, int y, int width, int height, int startColor, int endColor) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);

		int left = x;
		int top = y;
		int right = x + width;
		int bottom = y + height;
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos((double) right, (double) top, (double) 0).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos((double) left, (double) top, (double) 0).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos((double) left, (double) bottom, (double) 0).color(f5, f6, f7, f4).endVertex();
		vertexbuffer.pos((double) right, (double) bottom, (double) 0).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public int adjustX(MechGui gui, int x) {
		return gui.getOffsetFactorX() + x;
	}

	public int adjustY(MechGui gui, int y) {
		return gui.getOffsetFactorY() + y;
	}

	public boolean isInRect(MechGui gui, int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}

	public void drawPlayerSlots(MechGui gui, int posX, int posY, boolean center) {
		if (center)
			posX -= 81;
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				drawSlot(gui, posX + x * 18, posY + y * 18);

		for (int x = 0; x < 9; x++) {
			drawSlot(gui, posX + x * 18, posY + 58);
		}
	}

	public void drawSlot(MechGui gui, int posX, int posY) {
		posX = adjustX(gui, posX);
		posY = adjustY(gui, posY);
		setTextureSheet(BACKGROUND_SHEET);

		gui.drawTexturedModalRect(posX, posY, 0, 0, 18, 18);
	}

	public void drawString(MechGui gui, String string, int x, int y, int color) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);
		gui.mc.fontRenderer.drawString(string, x, y, color);
	}

	public void drawString(MechGui gui, String string, int x, int y) {
		drawString(gui, string, x, y, 16777215);
	}

	public void setTextureSheet(ResourceLocation textureLocation) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(textureLocation);
	}

	public void drawCenteredString(MechGui gui, String string, int y, int colour) {
		drawString(gui, string, (gui.getXSize() / 2 - gui.mc.fontRenderer.getStringWidth(string) / 2), y, colour);
	}

	public void drawCenteredString(MechGui gui, String string, int x, int y, int colour) {
		drawString(gui, string, (x - gui.mc.fontRenderer.getStringWidth(string) / 2), y, colour);
	}

	public int getStringWidth(String string) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(string);
	}

	public void drawSprite(MechGui gui, ISprite iSprite, int x, int y) {
		Sprite sprite = iSprite.getSprite(gui.provider);
		if (sprite != null) {
			if (sprite.hasTextureInfo()) {
				GlStateManager.color(1F, 1F, 1F);
				setTextureSheet(sprite.textureLocation);
				gui.drawTexturedModalRect(x + gui.getOffsetFactorX(), y + gui.getOffsetFactorY(), sprite.x, sprite.y, sprite.width, sprite.height);
			}
			if (sprite.hasStack()) {
				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				RenderHelper.enableGUIStandardItemLighting();

				RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
				itemRenderer.renderItemAndEffectIntoGUI(sprite.itemStack, x + gui.getOffsetFactorX(), y + gui.getOffsetFactorY());

				GlStateManager.disableLighting();
				GlStateManager.popMatrix();
			}
		}
	}

	public int getScaledBurnTime(int scale, int burnTime, int totalBurnTime) {
		return (int) (((float) burnTime / (float) totalBurnTime) * scale);
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

	public int getPercentage(int MaxValue, int CurrentValue) {
		if (CurrentValue == 0)
			return 0;
		return (int) ((CurrentValue * 100.0f) / MaxValue);
	}
}
