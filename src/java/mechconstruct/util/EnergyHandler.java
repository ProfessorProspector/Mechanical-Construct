package mechconstruct.util;

import mechconstruct.MechConstruct;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage {
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

    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger(MechConstruct.MOD_ID + ":energy", energy);
        tag.setInteger(MechConstruct.MOD_ID + ":energy_capacity", capacity);
        tag.setInteger(MechConstruct.MOD_ID + ":maxInput", maxReceive);
        tag.setInteger(MechConstruct.MOD_ID + ":maxOutput", maxExtract);
    }

    public void readFromNBT(NBTTagCompound tag) {
        energy = tag.getInteger(MechConstruct.MOD_ID + ":energy");
        capacity = tag.getInteger(MechConstruct.MOD_ID + ":energy_capacity");
        maxReceive = tag.getInteger(MechConstruct.MOD_ID + ":maxInput");
        maxExtract = tag.getInteger(MechConstruct.MOD_ID + ":maxOutput");
    }
}
