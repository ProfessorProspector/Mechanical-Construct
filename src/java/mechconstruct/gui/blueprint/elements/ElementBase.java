package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.proxy.MechClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementBase {

	public int x;
	public int y;
	public List<SpriteContainer> sprites = new ArrayList<>();

	public ElementBase(int x, int y, SpriteContainer... sprites) {
		this.sprites.addAll(Arrays.asList(sprites));
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y, Sprite... sprites) {
		for (Sprite sprite : sprites) {
			this.sprites.add(new SpriteContainer(sprite));
		}
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSprite(int index, Sprite sprite) {
		SpriteContainer container = sprites.get(index);
		container.sprite = sprite;
		sprites.set(index, container);
	}

	@SideOnly(Side.CLIENT)
	public void draw(MechGui gui) {
		for (SpriteContainer container : sprites) {
			MechClient.GUI_ASSEMBLER.drawSprite(gui, container.sprite, x + container.xOffset, y + container.yOffset);
		}
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
