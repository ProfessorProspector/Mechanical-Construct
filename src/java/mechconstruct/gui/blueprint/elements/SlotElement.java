package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.SlotType;
import net.minecraftforge.items.ItemStackHandler;

public class SlotElement extends ElementBase {
	protected ItemStackHandler slotInventory;
	protected SlotType type;
	int slotId, slotX, slotY;

	public SlotElement(ItemStackHandler slotInventory, int slotId, int slotX, int slotY, SlotType type, int x, int y) {
		super(type.getSprite(), x, y);
		this.type = type;
		this.slotInventory = slotInventory;
		this.slotId = slotId;
		this.slotX = slotX;
		this.slotY = slotY;
	}

	public SlotType getType() {
		return type;
	}

	public ItemStackHandler getSlotInventory() {
		return slotInventory;
	}

	public int getSlotId() {
		return slotId;
	}

	public int getSlotX() {
		return slotX;
	}

	public int getSlotY() {
		return slotY;
	}
}