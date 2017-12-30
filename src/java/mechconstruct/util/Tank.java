package mechconstruct.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class Tank extends FluidTank {
	Fluid lastFluid;
	int lastAmount;
	private String name;

	public Tank(String name, int capacity) {
		super(capacity);
		this.name = name;
	}

	public Tank(String name,
	            @Nullable
		            FluidStack fluidStack, int capacity) {
		super(fluidStack, capacity);
		this.name = name;
	}

	public Tank(String name, Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public FluidTank readFromNBT(NBTTagCompound compound) {
		// Allow to read empty tanks
		setFluid(null);
		return super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return super.writeToNBT(compound);
	}
}
