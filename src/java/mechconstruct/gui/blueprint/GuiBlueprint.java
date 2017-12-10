package mechconstruct.gui.blueprint;

import mechconstruct.gui.SlotType;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.gui.blueprint.elements.DummySlotElement;
import mechconstruct.gui.blueprint.elements.ElementBase;
import mechconstruct.gui.blueprint.elements.SlotElement;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiBlueprint {
	public IBlueprintProvider provider;
	public List<ElementBase> elements = new ArrayList<>();
	public List<SlotElement> slots = new ArrayList<>();
	public List<ButtonElement> buttonElements = new ArrayList<>();
	public int xSize = 0;
	public int ySize = 0;
	public int playerInvX = -1;
	public int playerInvY = -1;

	public GuiBlueprint(IBlueprintProvider provider) {
		this.provider = provider;
		setSize(176, 176);
	}

	public GuiBlueprint setSize(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		return this;
	}

	/**
	 * @param x the player's inventory slots will start drawing at this x position (this is the top left corner) [-1 to disable]
	 * @param y the player's inventory slots will start drawing at this y position (this is the top left corner) [-1 to disable]
	 * @return returns the blueprint for fast building of blueprints
	 */
	public GuiBlueprint setPlayerInvPos(int x, int y) {
		this.playerInvX = x;
		this.playerInvY = y;
		if (x > -1 && y > -1) {
			for (int row = 0; row < 3; ++row) {
				for (int column = 0; column < 9; ++column) {
					int index = column + row * 9 + 9;
					int xpos = x + (column * 18);
					int ypos = y + (row * 18);
					this.elements.add(new DummySlotElement(SlotType.NORMAL, xpos, ypos));
				}
			}
			for (int column = 0; column < 9; ++column) {
				int xpos = x + (column * 18);
				int ypos = y + 58;
				this.elements.add(new DummySlotElement(SlotType.NORMAL, xpos, ypos));
			}
		}
		return this;
	}

	public GuiBlueprint addElement(ElementBase element) {
		this.elements.add(element);
		return this;
	}

	public GuiBlueprint addButton(ButtonElement button) {
		this.buttonElements.add(button);
		this.elements.add(button);
		return this;
	}

	public GuiBlueprint addButton(int x, int y, int width, int height) {
		return addButton(new ButtonElement(x, y, width, height));
	}

	public GuiBlueprint addButton(String text, int color, int x, int y, int width, int height) {
		return addButton(new ButtonElement(text, color, x, y, width, height));
	}

	public GuiBlueprint addSlot(SlotElement slot) {
		this.slots.add(slot);
		this.elements.add(slot);
		return this;
	}

	public GuiBlueprint addSlot(SlotType type, int x, int y) {
		return addSlot(provider.getItemInventory(), type, x, y);
	}

	public GuiBlueprint addSlot(ItemStackHandler inventory, SlotType type, int x, int y) {
		addSlot(new SlotElement(inventory, slots.size(), x + type.getSlotOffsetX(), y + type.getSlotOffsetY(), type, x, y));
		return this;
	}

	public GuiBlueprint addSlot(int x, int y) {
		return addSlot(SlotType.NORMAL, x, y);
	}

	public GuiBlueprint addSlot(ItemStackHandler inventory, int x, int y) {
		return addSlot(inventory, SlotType.NORMAL, x, y);
	}

	public GuiTabBlueprint makeTabBlueprint(String name, Sprite sprite, ButtonElement.Action additionalAction) {
		GuiTabBlueprint tabBlueprint;
		if (additionalAction == null) {
			tabBlueprint = new GuiTabBlueprint(provider, name, sprite);
		} else {
			tabBlueprint = new GuiTabBlueprint(provider, name, sprite, additionalAction);
		}
		tabBlueprint.elements = elements;
		tabBlueprint.slots = slots;
		tabBlueprint.buttonElements = buttonElements;
		tabBlueprint.xSize = xSize;
		tabBlueprint.ySize = ySize;
		tabBlueprint.playerInvX = playerInvX;
		tabBlueprint.playerInvY = playerInvY;
		return tabBlueprint;
	}

	public GuiTabBlueprint makeTabBlueprint(String name, Sprite sprite) {
		return makeTabBlueprint(name, sprite, null);
	}
}
