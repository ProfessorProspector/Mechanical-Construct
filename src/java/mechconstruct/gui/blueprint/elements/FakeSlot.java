package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.Sprite;

public class FakeSlot extends ElementBase {
	public FakeSlot(int x, int y) {
		super(x, y, Sprite.FAKE_SLOT);
	}

	@Override
	public void draw(MechGui gui) {
		super.draw(gui);
	}
}