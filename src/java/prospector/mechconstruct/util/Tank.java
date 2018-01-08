package prospector.mechconstruct.util;

import prospector.mechconstruct.blockentities.BlockEntityMachine;
import prospector.mechconstruct.network.MechPacketManager;
import prospector.mechconstruct.network.PacketTankSync;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class Tank extends FluidTank {
	private String name;
	private FillFilter filter = f -> true;

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

	public Tank setFilter(FillFilter filter) {
		this.filter = filter;
		return this;
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

	@Override
	protected void onContentsChanged() {
		super.onContentsChanged();
		if (tile instanceof BlockEntityMachine && ((BlockEntityMachine) tile).hasFluidInventory()) {
			BlockPos pos = tile.getPos();
			MechPacketManager.networkWrapper.sendToAllAround(new PacketTankSync(pos, ((BlockEntityMachine) tile).getFluidInventory().serializeNBT()), new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128));
		}
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		if (filter.canFillFluidType(fluid)) {
			return super.canFillFluidType(fluid);
		} else {
			return false;
		}
	}

	public Tank setFillable(boolean fillable) {
		setCanFill(fillable);
		return this;
	}

	public Tank setDrainable(boolean drainable) {
		setCanDrain(drainable);
		return this;
	}

	public interface FillFilter {
		public boolean canFillFluidType(FluidStack fluid);
	}
}
