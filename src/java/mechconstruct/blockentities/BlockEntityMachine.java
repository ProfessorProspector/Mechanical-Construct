package mechconstruct.blockentities;

import mechconstruct.block.BlockMachine;
import mechconstruct.util.EnergyHandler;
import mechconstruct.util.EnergyUtils;
import mechconstruct.util.FluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BlockEntityMachine extends TileEntity implements ITickable {
    protected ItemStackHandler itemInventory;
    protected ItemStackHandler upgradeInventory;
    protected ItemStackHandler chargeInventory;
    protected EnergyHandler energyInventory;
    protected FluidHandler fluidInventory;
    protected int costMultiplier = 0;
    protected BlockMachine block;

    public BlockEntityMachine(int inventorySize, int energyCapacity, EnergyUtils.Bandwidth bandwidth, int upgradeSlots, FluidHandler.Tank... tanks) {
        this.itemInventory = inventorySize > 0 ? new ItemStackHandler(inventorySize) : null;
        this.upgradeInventory = upgradeSlots > 0 ? new ItemStackHandler(upgradeSlots) : null;
        this.chargeInventory = energyCapacity > 0 && bandwidth != EnergyUtils.Bandwidth.NONE ? new ItemStackHandler(1) : null;
        this.energyInventory = energyCapacity > 0 && bandwidth != EnergyUtils.Bandwidth.NONE ? new EnergyHandler(energyCapacity, bandwidth.getMaxInput(), bandwidth.getMaxOutput()) : null;
        this.fluidInventory = tanks.length > 0 ? new FluidHandler(tanks) : null;
    }

    public BlockEntityMachine(int energyCapacity, EnergyUtils.Bandwidth bandwidth, int upgradeSlots, FluidHandler.Tank... tanks) {
        this(0, energyCapacity, bandwidth, upgradeSlots, tanks);
    }

    public BlockEntityMachine(int inventorySize, FluidHandler.Tank... tanks) {
        this(inventorySize, 0, EnergyUtils.Bandwidth.NONE, 0, tanks);
    }

    public BlockEntityMachine(FluidHandler.Tank... tanks) {
        this(0, tanks);
    }


    @Override
    public void update() {
        machineTick();
    }

    public abstract void machineTick();

    public void setBlock(BlockMachine block) {
        this.block = block;
    }

    public BlockMachine getBlock() {
        return block;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (itemInventory != null) compound.setTag("item_inventory", itemInventory.serializeNBT());
        if (energyInventory != null) compound.setTag("energy_inventory", energyInventory.serializeNBT());
        if (fluidInventory != null) compound.setTag("fluid_inventory", fluidInventory.serializeNBT());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (itemInventory != null) itemInventory.deserializeNBT(compound.getCompoundTag("item_inventory"));
        if (energyInventory != null) energyInventory.deserializeNBT(compound.getCompoundTag("energy_inventory"));
        if (fluidInventory != null) fluidInventory.deserializeNBT(compound.getCompoundTag("fluid_inventory"));
        return super.writeToNBT(compound);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemInventory != null)
            return (T) itemInventory;
        if (capability == CapabilityEnergy.ENERGY && energyInventory != null)
            return (T) energyInventory;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && fluidInventory != null)
            return (T) fluidInventory;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemInventory != null)
            return true;
        if (capability == CapabilityEnergy.ENERGY && energyInventory != null)
            return true;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && fluidInventory != null)
            return true;
        return super.hasCapability(capability, facing);
    }

    public int getCostMultiplier() {
        return costMultiplier;
    }
}
