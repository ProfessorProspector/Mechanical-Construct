package mechconstruct.gui;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.blueprint.elements.SlotElement;
import mechconstruct.util.EnergyHandler;
import mechconstruct.util.FluidHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ContainerMachine extends Container {
	private final BlockEntityMachine machine;
	protected ItemStackHandler itemInventory;
	protected ItemStackHandler upgradeInventory;
	protected ItemStackHandler chargeInventory;
	protected EnergyHandler energyInventory;
	protected FluidHandler fluidInventory;

	public ContainerMachine(BlockEntityMachine machine, EntityPlayer player) {
		this.machine = machine;
		itemInventory = machine.getItemInventory();
		upgradeInventory = machine.getUpgradeInventory();
		chargeInventory = machine.getChargeInventory();
		energyInventory = machine.getEnergyInventory();
		fluidInventory = machine.getFluidInventory();
		addSlots();
		addPlayerSlots(player);
	}

	private void addSlots() {
		for (SlotElement slotElement : machine.getGuiBlueprint().slots) {
			addSlotToContainer(slotElement.getSlot());
		}
	}

	private void addPlayerSlots(EntityPlayer player) {
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				int index = column + row * 9 + 9;
				int x = machine.getGuiBlueprint().playerInvX + 1 + (column * 18);
				int y = machine.getGuiBlueprint().playerInvY + 1 + (row * 18);
				addSlotToContainer(new Slot(player.inventory, index, x, y));
			}
		}
		for (int column = 0; column < 9; ++column) {
			int x = machine.getGuiBlueprint().playerInvX + 1 + (column * 18);
			int y = machine.getGuiBlueprint().playerInvY + 1 + 58;
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

			if (index < itemInventory.getSlots()) {
				if (!this.mergeItemStack(itemstack1, itemInventory.getSlots(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, itemInventory.getSlots(), false)) {
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
		return machine.canInteractWith(playerIn);
	}
}
