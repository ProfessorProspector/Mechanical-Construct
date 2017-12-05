package mechconstruct.gui.blueprint.elements;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.MechGuiButton;

public class ButtonElement extends ElementBase {
	protected String text;
	protected int width;
	protected int height;
	protected MechGuiButton button;
	protected ButtonAction action;

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

	public static interface ButtonAction {
		public void execute(BlockEntityMachine machine);
	}
}
