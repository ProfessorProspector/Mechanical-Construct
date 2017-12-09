package mechconstruct.gui;

import mechconstruct.gui.blueprint.GuiTabBlueprint;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.elements.SlotElement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MechContainer extends Container {
	public final GuiTabBlueprint blueprint;
	public final IBlueprintProvider provider;

	public MechContainer(IBlueprintProvider provider, GuiTabBlueprint blueprint, EntityPlayer player) {
		this.blueprint = blueprint;
		this.provider = provider;
		addSlots();
		if (blueprint.playerInvX > -1 && blueprint.playerInvY > -1) {
			addPlayerSlots(player);
		}
	}

	private void addSlots() {
		for (SlotElement slot : blueprint.slots) {
			addSlotToContainer(new SlotItemHandler(slot.getSlotInventory(), slot.getSlotId(), slot.getSlotX(), slot.getSlotY()));
		}
	}

	private void addPlayerSlots(EntityPlayer player) {
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				int index = column + row * 9 + 9;
				int x = blueprint.playerInvX + 1 + (column * 18);
				int y = blueprint.playerInvY + 1 + (row * 18);
				addSlotToContainer(new Slot(player.inventory, index, x, y));
			}
		}
		for (int column = 0; column < 9; ++column) {
			int x = blueprint.playerInvX + 1 + (column * 18);
			int y = blueprint.playerInvY + 1 + 58;
			addSlotToContainer(new Slot(player.inventory, column, x, y));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < provider.getItemInventory().getSlots()) {
				if (!this.mergeItemStack(itemstack1, provider.getItemInventory().getSlots(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, provider.getItemInventory().getSlots(), false)) {
				return null;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return provider.canInteractWith(playerIn);
	}
}
