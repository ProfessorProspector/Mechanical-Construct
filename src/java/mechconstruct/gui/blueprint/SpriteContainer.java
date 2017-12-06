package mechconstruct.gui.blueprint;

import mechconstruct.gui.Sprite;

public class SpriteContainer {
	public Sprite sprite;
	public int xOffset;
	public int yOffset;

	public SpriteContainer(Sprite sprite, int xOffset, int yOffset) {
		this.sprite = sprite;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public SpriteContainer(Sprite sprite) {
		this(sprite, 0, 0);
	}
}
