package mechconstruct.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

public class FluidHandler implements INBTSerializable<NBTTagCompound> {

    public HashMap<String, Tank> tanks;

    public FluidHandler(Tank... tanks) {
        for (Tank tank : tanks) {
            this.tanks.put(tank.getName(), tank);
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
            tank.readFromNBT(compound);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        for (Tank tank : getTanks()) {
            tank.writeToNBT(compound);
        }
    }

    public class Tank extends FluidTank {
        private String name;

        public Tank(String name, int capacity) {
            super(capacity);
            this.name = name;
        }

        public Tank(String name, @Nullable FluidStack fluidStack, int capacity) {
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
            if (compound.hasKey(name)) {
                // Allow to read empty tanks
                setFluid(null);

                NBTTagCompound tankData = compound.getCompoundTag(name);
                super.readFromNBT(tankData);
            }
            return this;
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            return super.writeToNBT(compound);
        }
    }
}
