package mechconstruct.gui.blueprint.elements;

import jline.internal.Nullable;
import mechconstruct.MechConstruct;
import mechconstruct.gui.GuiMachine;
import mechconstruct.gui.Sprite;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElementBase {

	protected final int x;
	protected final int y;
	protected Sprite sprite;

	public ElementBase(Sprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y) {
		this(null, x, y);
	}

	/**
	 * @return the element's sprite **MAY BE NULL**
	 */
	@Nullable
	public Sprite getSprite() {
		return sprite;
	}

	@SideOnly(Side.CLIENT)
	public void draw(GuiMachine gui) {
		if (sprite != null) {
			GlStateManager.color(1F, 1F, 1F);
			MechConstruct.proxy.getGuiAssembler().setTextureSheet(sprite.textureLocation);
			gui.drawTexturedModalRect(x + gui.getOffsetFactorX(), y + gui.getOffsetFactorY(), sprite.x, sprite.y, sprite.width, sprite.height);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
