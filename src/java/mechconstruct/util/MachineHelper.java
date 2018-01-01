package mechconstruct.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemStackHandler;

public class MachineHelper {
	public static boolean fillTankFromSlot() {

	}

	public static boolean fillTankFromSlot(FluidHandler fluidHandler, String tank, ItemStackHandler inventory, String inputSlot, String outputSlot) {
		ItemStack input = inv.getStackInSlot(inputSlot);
		ItemStack output = inv.getStackInSlot(outputSlot);

		IFluidHandlerItem inputFluidHandler = getFluidHandler(input);

		if (inputFluidHandler != null) {

			/*
			 * Making a simulation to check if the fluid can be drained into the
			 * fluidhandler.
			 */
			if (FluidUtil.tryFluidTransfer(fluidHandler, inputFluidHandler,
				inputFluidHandler.getTankProperties()[0].getCapacity(), false) != null) {

				// Changes are really applied and the fluid is drained.
				FluidStack drained = FluidUtil.tryFluidTransfer(fluidHandler, inputFluidHandler,
					inputFluidHandler.getTankProperties()[0].getCapacity(), true);

				/*
				 * If the drained container doesn't disappear we need to update
				 * the inventory accordingly.
				 */
				if (drained != null && inputFluidHandler.getContainer() != ItemStack.EMPTY)
					if (output.isEmpty()) {
						inv.setInventorySlotContents(outputSlot, inputFluidHandler.getContainer());
						inv.decrStackSize(inputSlot, 1);
					} else {

						/*
						 * When output is not EMPTY, it is needed to check if
						 * the two stacks can be merged together, there was no
						 * simple way to make that check before.
						 */
						if (ItemUtils.isItemEqual(output, inputFluidHandler.getContainer(), true, true)) {
							inv.getStackInSlot(outputSlot).setCount(inv.getStackInSlot(outputSlot).getCount() + 1);
							inv.decrStackSize(inputSlot, 1);
						} else {

							/*
							 * Due to the late check of stacks merge we need to
							 * reverse any changes made to the FluidHandlers
							 * when the merge fail.
							 */
							FluidUtil.tryFluidTransfer(inputFluidHandler, fluidHandler, drained.amount, true);
							return false;
						}
					}
				return true;
			}
		}
		return false;
	}

	public static void drainTankFromSlot(FluidHandler fluidHandler, String tank, int inputSlot, int outputSlot) {

	}
}
