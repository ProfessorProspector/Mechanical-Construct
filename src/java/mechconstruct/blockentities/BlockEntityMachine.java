package mechconstruct.block;

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

public class BlockEntityMachine extends TileEntity implements ITickable {
    protected ItemStackHandler itemInventory;
    protected ItemStackHandler upgradeInventory;
    protected ItemStackHandler chargeInventory;
    protected EnergyHandler energyInventory;
    protected FluidHandler fluidInventory;
    protected int costMultiplier = 0;
    protected BlockMachine block;
    protected EnumFacing facing;
    protected boolean active;

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
    public final void update() {
        block.machineTick(this);
    }

    public EnumFacing getFacing() {
        if (facing == null) {
            facing = block.getDefaultState().getValue(BlockMachine.FACING);
            active = block.getDefaultState().getValue(BlockMachine.ACTIVE);
        }
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BlockMachine getBlock() {
        return block;
    }

    public void setBlock(BlockMachine block) {
        this.block = block;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.setTag("item_inventory", itemInventory.serializeNBT());
        compound.setTag("energy_inventory", energyInventory.serializeNBT());
        compound.setTag("fluid_inventory", fluidInventory.serializeNBT());
        compound.setString("facing", facing.getName());
        compound.setBoolean("active", active);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        itemInventory.deserializeNBT(compound.getCompoundTag("item_inventory"));
        energyInventory.deserializeNBT(compound.getCompoundTag("energy_inventory"));
        fluidInventory.deserializeNBT(compound.getCompoundTag("fluid_inventory"));
        facing = EnumFacing.byName(compound.getString("facing"));
        active = compound.getBoolean("active");
        return super.writeToNBT(compound);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) itemInventory;
        if (capability == CapabilityEnergy.ENERGY) return (T) energyInventory;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) fluidInventory;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return (itemInventory != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || (energyInventory != null && capability == CapabilityEnergy.ENERGY) || (fluidInventory != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
    }

    public int getCostMultiplier() {
        return costMultiplier;
    }
}
