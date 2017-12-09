package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.SlotType;
import net.minecraftforge.items.SlotItemHandler;

public class SlotElement extends ElementBase {
	protected SlotItemHandler slot;
	protected SlotType type;

	public SlotElement(SlotItemHandler slot, SlotType type, int x, int y) {
		super(type.getSprite(), x, y);
		this.slot = slot;
		this.type = type;
	}

	public SlotItemHandler getSlot() {
		return slot;
	}

	public SlotType getType() {
		return type;
	}
}