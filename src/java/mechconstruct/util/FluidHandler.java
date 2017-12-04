package mechconstruct.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

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
