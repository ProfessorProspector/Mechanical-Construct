package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.GuiMachine;
import mechconstruct.gui.Sprite;
import mechconstruct.proxy.MechClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElementBase {

	public int x;
	public int y;
	public Sprite sprite;

	public ElementBase(Sprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y) {
		this(null, x, y);
	}

	public ElementBase setSprite(Sprite sprite) {
		this.sprite = sprite;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void draw(GuiMachine gui) {
		MechClient.GUI_ASSEMBLER.drawSprite(gui, sprite, x, y);
	}

	public ElementBase setX(int x) {
		this.x = x;
		return this;
	}

	public ElementBase setY(int y) {
		this.y = y;
		return this;
	}
}
