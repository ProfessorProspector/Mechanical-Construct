package mechconstruct.gui.blueprint.elements;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.MechGuiButton;
import mechconstruct.gui.Sprite;

public class ButtonElement extends ElementBase {
	protected String text;
	protected int width;
	protected int height;
	protected MechGuiButton button;
	protected ButtonAction action;
	protected boolean disableDefaultRenderer = true;
	protected int customSpriteOffsetX = 0;
	protected int customSpriteOffsetY = 0;

	public ButtonElement(String text, int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	public ButtonElement(int x, int y, int width, int height) {
		this("", x, y, width, height);
	}

	public String getText() {
		return text;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public MechGuiButton getButton() {
		return button;
	}

	public ButtonAction getAction() {
		return action;
	}

	public ButtonElement setAction(ButtonAction action) {
		this.action = action;
		return this;
	}

	public boolean isDisableDefaultRenderer() {
		return disableDefaultRenderer;
	}

	public ButtonElement setDisableDefaultRenderer(boolean disableDefaultRenderer) {
		this.disableDefaultRenderer = disableDefaultRenderer;
		return this;
	}

	public ButtonElement setCustomSprite(Sprite sprite, int offsetX, int offsetY) {
		this.sprite = sprite;
		this.customSpriteOffsetX = offsetX;
		this.customSpriteOffsetY = offsetY;
		return this;
	}

	public ButtonElement setCustomSprite(Sprite sprite) {
		return setCustomSprite(sprite, 0, 0);
	}

	public Sprite getCustomSprite() {
		return this.sprite;
	}

	public boolean hasCustomSprite() {
		return this.sprite != null;
	}

	public int getCustomSpriteOffsetX() {
		return customSpriteOffsetX;
	}

	public int getCustomSpriteOffsetY() {
		return customSpriteOffsetY;
	}

	public interface ButtonAction {
		void execute(BlockEntityMachine machine);
	}
}
