package mechconstruct.gui;

import com.mojang.realmsclient.util.Pair;
import mechconstruct.gui.blueprint.GuiTabBlueprint;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.elements.SlotElement;
import mechconstruct.gui.slot.MechSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.MutableTriple;

import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class MechContainer extends Container {
	public final GuiTabBlueprint blueprint;
	public final IBlueprintProvider provider;
	private final ArrayList<MutableTriple<IntSupplier, IntConsumer, Integer>> integerValues = new ArrayList<>();
	private final ArrayList<MutableTriple<IntSupplier, IntConsumer, Short>> shortValues = new ArrayList<>();
	private Integer[] integerParts;

	public MechContainer(IBlueprintProvider provider, GuiTabBlueprint blueprint, EntityPlayer player) {
		this.blueprint = blueprint;
		this.provider = provider;

		for (final Pair<IntSupplier, IntConsumer> syncable : blueprint.shortSyncables)
			this.shortValues.add(MutableTriple.of(syncable.first(), syncable.second(), (short) 0));
		this.shortValues.trimToSize();

		for (final Pair<IntSupplier, IntConsumer> syncable : blueprint.intSyncables) {
			this.integerValues.add(MutableTriple.of(syncable.first(), syncable.second(), 0));
		}
		this.integerValues.trimToSize();

		this.integerParts = new Integer[this.integerValues.size()];
		addSlots();
		if (blueprint.playerInvX > -1 && blueprint.playerInvY > -1) {
			addPlayerSlots(player);
		}
	}

	private void addSlots() {
		for (SlotElement slot : blueprint.slots) {
			addSlotToContainer(new MechSlot(provider, slot.getSlotInventory(), slot.getSlotId(), slot.getSlotX(), slot.getSlotY()).setFilter(slot.filter));
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
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < blueprint.slots.size()) {
				if (!this.mergeItemStack(itemstack1, blueprint.slots.size(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, blueprint.slots.size(), false)) {
				return ItemStack.EMPTY;
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

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (final IContainerListener listener : this.listeners) {

			int i = 0;
			if (!this.shortValues.isEmpty())
				for (final MutableTriple<IntSupplier, IntConsumer, Short> value : this.shortValues) {
					final short supplied = (short) value.getLeft().getAsInt();
					if (supplied != value.getRight()) {

						listener.sendWindowProperty(this, i, supplied);
						value.setRight(supplied);
					}
					i++;
				}

			if (!this.integerValues.isEmpty())
				for (final MutableTriple<IntSupplier, IntConsumer, Integer> value : this.integerValues) {
					final int supplied = value.getLeft().getAsInt();
					if (supplied != value.getRight()) {

						listener.sendWindowProperty(this, i, supplied >> 16);
						listener.sendWindowProperty(this, i + 1, (short) (supplied & 0xFFFF));
						value.setRight(supplied);
					}
					i += 2;
				}
		}
	}

	@Override
	public void addListener(final IContainerListener listener) {
		super.addListener(listener);

		int i = 0;
		if (!this.shortValues.isEmpty())
			for (final MutableTriple<IntSupplier, IntConsumer, Short> value : this.shortValues) {
				final short supplied = (short) value.getLeft().getAsInt();

				listener.sendWindowProperty(this, i, supplied);
				value.setRight(supplied);
				i++;
			}

		if (!this.integerValues.isEmpty())
			for (final MutableTriple<IntSupplier, IntConsumer, Integer> value : this.integerValues) {
				final int supplied = value.getLeft().getAsInt();

				listener.sendWindowProperty(this, i, supplied >> 16);
				listener.sendWindowProperty(this, i + 1, (short) (supplied & 0xFFFF));
				value.setRight(supplied);
				i += 2;
			}

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(final int id, final int value) {

		if (id < this.shortValues.size()) {
			this.shortValues.get(id).getMiddle().accept((short) value);
			this.shortValues.get(id).setRight((short) value);
		} else if (id - this.shortValues.size() < this.integerValues.size() * 2) {

			if ((id - this.shortValues.size()) % 2 == 0)
				this.integerParts[(id - this.shortValues.size()) / 2] = value;
			else {
				this.integerValues.get((id - this.shortValues.size()) / 2).getMiddle().accept(
					(this.integerParts[(id - this.shortValues.size()) / 2] & 0xFFFF) << 16 | value & 0xFFFF);
			}
		}
	}
}
