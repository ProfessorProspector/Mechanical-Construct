package mechconstruct.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Collection;
import java.util.HashMap;

public class FluidHandler implements INBTSerializable<NBTTagCompound> {

	public HashMap<String, Tank> tanks = new HashMap<>();

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
