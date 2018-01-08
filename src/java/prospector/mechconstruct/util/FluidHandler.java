package prospector.mechconstruct.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.HashMap;

public class FluidHandler implements INBTSerializable<NBTTagCompound> {

	public HashMap<String, Tank> tanks = new HashMap<>();

	public FluidHandler(Tank... tanks) {
		for (Tank tank : tanks) {
			this.tanks.put(tank.getName(), tank);
		}
	}

	/* Fluid Inventory Utils */

	public Tank getTank(String tank, FluidStack fluidStack) {
		return getTank(tank);
	}

	public Tank getTank(String tank, Fluid fluid, int amount) {
		return getTank(tank, new FluidStack(fluid, amount));
	}

	public void setFluid(String tank, FluidStack fluidStack) {
		FluidStack lastFluid = getTank(tank).getFluid();
		getTank(tank).setFluid(fluidStack);
		if (lastFluid != getTank(tank).getFluid()) {
			getTank(tank).onContentsChanged();
		}
	}

	public void setFluid(String tank, Fluid fluid, int amount) {
		setFluid(tank, new FluidStack(fluid, amount));
	}

	public int fillTank(String tank, FluidStack fluidStack) {
		return getTank(tank).fill(fluidStack, true);
	}

	public int fillTank(String tank, Fluid fluid, int amount) {
		return fillTank(tank, new FluidStack(fluid, amount));
	}

	public int simulateFillTank(String tank, FluidStack fluidStack) {
		return getTank(tank).fill(fluidStack, false);
	}

	public int simulateFillTank(String tank, Fluid fluid, int amount) {
		return simulateFillTank(tank, new FluidStack(fluid, amount));
	}

	public FluidStack drainTank(String tank, int amount) {
		return getTank(tank).drain(amount, true);
	}

	public FluidStack drainTank(String tank, FluidStack fluidStack) {
		return getTank(tank).drain(fluidStack, true);
	}

	public FluidStack drainTank(String tank, Fluid fluid, int amount) {
		return drainTank(tank, new FluidStack(fluid, amount));
	}

	public FluidStack simulateDrainTank(String tank, int amount) {
		return getTank(tank).drain(amount, false);
	}

	public FluidStack simulateDrainTank(String tank, FluidStack fluidStack) {
		return getTank(tank).drain(fluidStack, false);
	}

	public FluidStack simulateDrainTank(String tank, Fluid fluid, int amount) {
		return simulateDrainTank(tank, new FluidStack(fluid, amount));
	}

	public void setTiles(TileEntity tile) {
		for (Tank tank : getTanks()) {
			tank.setTileEntity(tile);
		}
	}

	public Collection<Tank> getTanks() {
		return tanks.values();
	}

	public HashMap<String, Tank> getTankMap() {
		return tanks;
	}

	public Tank getTank(String name) {
		return tanks.get(name);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		for (Tank tank : getTanks()) {
			compound.setTag(tank.getName(), tank.writeToNBT(new NBTTagCompound()));
		}
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		for (Tank tank : getTanks()) {
			tank.readFromNBT(compound.getCompoundTag(tank.getName()));
		}
	}

}
