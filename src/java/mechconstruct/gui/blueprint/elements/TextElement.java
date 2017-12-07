package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.GuiMachine;
import mechconstruct.proxy.MechClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TextElement extends ElementBase {
	protected String text;
	protected int color;
	protected boolean centered = false;
	protected int clipTo = -1;

	public TextElement(String text, int color, int x, int y) {
		super(x, y);
		this.text = text;
		this.color = color;
	}

	public TextElement(String text, int color, int x, int y, int clipTo) {
		this(text, color, x, y);
		this.clipTo = clipTo;
	}

	public TextElement(String text, int color, int x, int y, boolean centered) {
		this(text, color, x, y);
		this.centered = centered;
	}

	public TextElement(String text, int color, int y, boolean centered) {
		this(text, color, -1, y);
		this.centered = centered;
	}

	public TextElement(String text, int color, int x, int y, boolean centered, int clipTo) {
		this(text, color, x, y, centered);
		this.clipTo = clipTo;
	}

	public TextElement(String text, int color, int y, boolean centered, int clipTo) {
		this(text, color, y, centered);
		this.clipTo = clipTo;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(GuiMachine gui) {
		String string = text;
		if (clipTo > -1 && gui.mc.fontRenderer.getStringWidth(string) > clipTo) {
			string = gui.mc.fontRenderer.trimStringToWidth(text, clipTo - gui.mc.fontRenderer.getStringWidth("..."));
			string = string + "...";
		}
		if (centered) {
			if (x > -1) {
				MechClient.GUI_ASSEMBLER.drawCenteredString(gui, string, x, y, color);
			} else {
				MechClient.GUI_ASSEMBLER.drawCenteredString(gui, string, y, color);
			}
		} else if (x > -1) {
			MechClient.GUI_ASSEMBLER.drawString(gui, string, x, y, color);
		}

	}
}