package mechconstruct.gui.slot;

import mechconstruct.gui.blueprint.IBlueprintProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class MechSlot extends SlotItemHandler {
	public final IBlueprintProvider provider;
	public SlotFilter filter = (slot, stack) -> true;

	public MechSlot(IBlueprintProvider provider, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.provider = provider;
	}

	public MechSlot setFilter(SlotFilter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public boolean isItemValid(
		@Nonnull
			ItemStack stack) {
		return !stack.isEmpty() && filter.isItemValid(this, stack) && super.isItemValid(stack);
	}

	public interface SlotFilter {
		public boolean isItemValid(MechSlot slot, ItemStack stack);
	}
}
