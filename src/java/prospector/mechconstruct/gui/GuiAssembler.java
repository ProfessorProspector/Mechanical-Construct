package prospector.mechconstruct.gui;

import prospector.mechconstruct.gui.blueprint.ISprite;
import prospector.mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiAssembler extends GuiAssemblerServer {

	@Override
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

	@Override
	public void drawRect(MechGui gui, int x, int y, int width, int height, int colour) {
		drawGradientRect(gui, x, y, width, height, colour, colour);
	}

	/*
		Taken from Gui
	*/
	@Override
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

	@Override
	public int adjustX(MechGui gui, int x) {
		return gui.getOffsetFactorX() + x;
	}

	@Override
	public int adjustY(MechGui gui, int y) {
		return gui.getOffsetFactorY() + y;
	}

	@Override
	public boolean isInRect(MechGui gui, int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}

	@Override
	public void drawSimpleTooltip(MechGui gui, int mouseX, int mouseY, List<String> lines) {
		GuiUtils.drawHoveringText(lines, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRenderer);
		GlStateManager.disableLighting();
		GlStateManager.color(1, 1, 1, 1);
	}

	@Override
	public void drawPercentTooltip(MechGui gui, int mouseX, int mouseY, int current, int max, String unitLabel, String percentageLabel) {
		int percentage = getPercentage(max, current);
		drawSimpleTooltip(gui, mouseX, mouseY,
			TextFormatting.GOLD + "" + current + "/" + max + (unitLabel.isEmpty() ? "" : " " + unitLabel),
			getPercentageColour(percentage) + "" + percentage + "%" + TextFormatting.GRAY + (percentageLabel.isEmpty() ? "" : " " + percentageLabel));
	}

	@Override

	public void drawSimpleTooltip(MechGui gui, int mouseX, int mouseY, String... lines) {
		drawSimpleTooltip(gui, mouseX, mouseY, Arrays.asList(lines));
	}

	@Override
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

	@Override
	public void drawSlot(MechGui gui, int posX, int posY) {
		posX = adjustX(gui, posX);
		posY = adjustY(gui, posY);
		setTextureSheet(BACKGROUND_SHEET);

		gui.drawTexturedModalRect(posX, posY, 0, 0, 18, 18);
	}

	@Override
	public void drawString(MechGui gui, String string, int x, int y, int color) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);
		gui.mc.fontRenderer.drawString(string, x, y, color);
	}

	@Override
	public void drawString(MechGui gui, String string, int x, int y) {
		drawString(gui, string, x, y, 16777215);
	}

	@Override
	public void setTextureSheet(ResourceLocation textureLocation) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(textureLocation);
	}

	@Override
	public void drawCenteredString(MechGui gui, String string, int y, int color) {
		drawString(gui, string, (gui.getXSize() / 2 - gui.mc.fontRenderer.getStringWidth(string) / 2), y, color);
	}

	@Override
	public void drawCenteredString(MechGui gui, String string, int x, int y, int color) {
		drawString(gui, string, (x - gui.mc.fontRenderer.getStringWidth(string) / 2), y, color);
	}

	@Override
	public int getStringWidth(String string) {
		return Minecraft.getMinecraft().fontRenderer.getStringWidth(string);
	}

	@Override
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

	@Override
	public void drawFluid(MechGui gui, FluidStack fluid, int x, int y, int width, int height, int maxCapacity) {
		x = adjustX(gui, x);
		y = adjustY(gui, y);
		setTextureSheet(TextureMap.LOCATION_BLOCKS_TEXTURE);
		y += height;
		final ResourceLocation still = fluid.getFluid().getStill(fluid);
		final TextureAtlasSprite sprite = gui.mc.getTextureMapBlocks().getAtlasSprite(still.toString());

		final int drawHeight = (int) (fluid.amount / (maxCapacity * 1F) * height);
		final int iconHeight = sprite.getIconHeight();
		int offsetHeight = drawHeight;

		int iteration = 0;
		while (offsetHeight != 0) {
			final int curHeight = offsetHeight < iconHeight ? offsetHeight : iconHeight;
			gui.drawTexturedModalRect(x, y - offsetHeight, sprite, width, curHeight);
			offsetHeight -= curHeight;
			iteration++;
			if (iteration > 100)
				break;
		}
	}
}
