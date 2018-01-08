package prospector.mechconstruct.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;

public class EnergyHandler extends EnergyStorage implements INBTSerializable<NBTTagCompound> {

	public EnergyHandler(int capacity) {
		super(capacity);
	}

	public EnergyHandler(int capacity, int maxIO) {
		super(capacity, maxIO);
	}

	public EnergyHandler(int capacity, int maxInput, int maxOutput) {
		super(capacity, maxInput, maxOutput);
	}

	public EnergyHandler(int capacity, int maxInput, int maxOutput, int energy) {
		super(capacity, maxInput, maxOutput, energy);
	}

	/**
	 * @return the corresponding Bandwidth for the max IO values. Returns null if not a valid (custom) bandwidth.
	 */
	@Nullable
	public EnergyUtils.Bandwidth getBandwidth() {
		for (EnergyUtils.Bandwidth bandwidth : EnergyUtils.Bandwidth.values()) {
			if (maxReceive == bandwidth.getMaxInput() && maxExtract == bandwidth.getMaxOutput()) {
				return bandwidth;
			}
		}
		return null;
	}

	public int getMaxInput() {
		return maxReceive;
	}

	public void setMaxInput(int maxInput) {
		this.maxReceive = maxInput;
	}

	public int getMaxOutput() {
		return maxExtract;
	}

	public void setMaxOutput(int maxOutput) {
		this.maxExtract = maxOutput;
	}

	public int getCapacity() {
		return getMaxEnergyStored();
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getEnergy() {
		return getEnergyStored();
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("energy", energy);
		compound.setInteger("energy_capacity", capacity);
		compound.setInteger("maxInput", maxReceive);
		compound.setInteger("maxOutput", maxExtract);
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		energy = compound.getInteger("energy");
		capacity = compound.getInteger("energy_capacity");
		maxReceive = compound.getInteger("maxInput");
		maxExtract = compound.getInteger("maxOutput");
	}
}
