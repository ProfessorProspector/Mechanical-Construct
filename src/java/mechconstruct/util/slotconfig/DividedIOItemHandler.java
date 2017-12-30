package mechconstruct.util.slotconfig;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.List;


/*
 * A wrapper that composes another IItemHandlerModifiable, exposing only a list of the composed slots.
 * Shifting of slot indices is handled automatically for you.
 * Based on Forge's RangedWrapper.
*/

public class DividedIOItemHandler implements IItemHandlerModifiable {

	public final List<SlotCompound> slots;
	private final IItemHandlerModifiable compose;

	public DividedIOItemHandler(IItemHandlerModifiable compose, List<SlotCompound> slots) {
		this.compose = compose;
		this.slots = slots;
	}

	@Override
	public int getSlots() {
		return slots.size();
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(int slot) {
		if (checkSlot(slot)) {
			return compose.getStackInSlot(slots.get(slot).getId());
		}

		return ItemStack.EMPTY;
	}

	@Override
	@Nonnull
	public ItemStack insertItem(int slot,
	                            @Nonnull
		                            ItemStack stack, boolean simulate) {
		if (checkSlot(slot) && slots.get(slot).getSlotConfig().isInsert()) {
			return compose.insertItem(slots.get(slot).getId(), stack, simulate);
		}

		return stack;
	}

	@Override
	@Nonnull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (checkSlot(slot) && slots.get(slot).getSlotConfig().isExtact()) {
			return compose.extractItem(slots.get(slot).getId(), amount, simulate);
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setStackInSlot(int slot,
	                           @Nonnull
		                           ItemStack stack) {
		if (checkSlot(slot)) {
			compose.setStackInSlot(slots.get(slot).getId(), stack);
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		if (checkSlot(slot)) {
			return compose.getSlotLimit(slots.get(slot).getId());
		}

		return 0;
	}

	private boolean checkSlot(int localSlot) {
		return localSlot < slots.size() && localSlot >= 0;
	}

}
