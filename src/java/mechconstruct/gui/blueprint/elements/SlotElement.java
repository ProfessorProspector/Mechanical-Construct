package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.SlotType;
import net.minecraft.inventory.Slot;

public class SlotElement extends ElementBase {
	protected Slot slot;
	protected SlotType type;

	public SlotElement(Slot slot, SlotType type, int x, int y) {
		super(type.getSprite(), x, y);
		this.slot = slot;
		this.type = type;
	}

	public Slot getSlot() {
		return slot;
	}

	public SlotType getType() {
		return type;
	}
}