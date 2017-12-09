package mechconstruct.blockentities;

import mechconstruct.block.BlockMachine;
import mechconstruct.gui.MechContainer;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.GuiTabBlueprint;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.util.EnergyHandler;
import mechconstruct.util.EnergyUtils;
import mechconstruct.util.FluidHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntityMachine extends TileEntity implements ITickable, IBlueprintProvider {
	protected ItemStackHandler itemInventory;
	protected ItemStackHandler upgradeInventory;
	protected ItemStackHandler chargeInventory;
	protected EnergyHandler energyInventory;
	protected FluidHandler fluidInventory;
	protected boolean hasItemInventory = false;
	protected boolean hasUpgradeInventory = false;
	protected boolean hasEnergyChargeInventories = false;
	protected boolean hasFluidInventory = false;
	protected int costMultiplier = 0;
	protected BlockMachine block;
	protected GuiBlueprint mainBlueprint;
	protected GuiTabBlueprint currentTab;

	public BlockEntityMachine(int inventorySize, int energyCapacity, EnergyUtils.Bandwidth bandwidth, int upgradeSlots, FluidHandler.Tank... tanks) {
		if (inventorySize > 0) {
			this.itemInventory = new ItemStackHandler(inventorySize) {
				@Override
				protected void onContentsChanged(int slot) {
					BlockEntityMachine.this.markDirty();
				}
			};
			this.hasItemInventory = true;
		}
		if (upgradeSlots > 0) {
			if (upgradeSlots > 21) {
				upgradeSlots = 21;
			}
			this.upgradeInventory = new ItemStackHandler(upgradeSlots) {
				@Override
				protected void onContentsChanged(int slot) {
					BlockEntityMachine.this.markDirty();
				}
			};
			this.hasUpgradeInventory = true;
		}
		if (energyCapacity > 0 && bandwidth != EnergyUtils.Bandwidth.NONE) {
			this.energyInventory = new EnergyHandler(energyCapacity, bandwidth.getMaxInput(), bandwidth.getMaxOutput());
			this.chargeInventory = new ItemStackHandler(1) {
				@Override
				protected void onContentsChanged(int slot) {
					BlockEntityMachine.this.markDirty();
				}
			};
			this.hasEnergyChargeInventories = true;
		}
		if (tanks.length > 0) {
			this.fluidInventory = new FluidHandler(tanks);
			this.hasFluidInventory = true;
		}
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

	public BlockMachine getBlock() {
		return block;
	}

	public void setBlock(BlockMachine block) {
		this.block = block;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (hasItemInventory) {
			itemInventory.deserializeNBT(compound.getCompoundTag("item_inventory"));
		}
		if (hasUpgradeInventory) {
			upgradeInventory.deserializeNBT(compound.getCompoundTag("upgrade_inventory"));
		}
		if (hasEnergyChargeInventories) {
			energyInventory.deserializeNBT(compound.getCompoundTag("energy_inventory"));
			chargeInventory.deserializeNBT(compound.getCompoundTag("charge_inventory"));
		}
		if (hasFluidInventory) {
			fluidInventory.deserializeNBT(compound.getCompoundTag("fluid_inventory"));
		}
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (hasItemInventory) {
			compound.setTag("item_inventory", itemInventory.serializeNBT());
		}
		if (hasUpgradeInventory) {
			compound.setTag("upgrade_inventory", upgradeInventory.serializeNBT());
		}
		if (hasEnergyChargeInventories) {
			compound.setTag("energy_inventory", energyInventory.serializeNBT());
			compound.setTag("charge_inventory", chargeInventory.serializeNBT());
		}
		if (hasFluidInventory) {
			compound.setTag("fluid_inventory", fluidInventory.serializeNBT());
		}
		return super.writeToNBT(compound);
	}

	@Nullable
	@Override
	public <T> T getCapability(
		@Nonnull
			Capability<T> capability,
		@Nullable
			EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemInventory != null)
			return (T) itemInventory;
		if (capability == CapabilityEnergy.ENERGY && energyInventory != null)
			return (T) energyInventory;
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && fluidInventory != null)
			return (T) fluidInventory;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(
		@Nonnull
			Capability<?> capability,
		@Nullable
			EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemInventory != null)
			return true;
		if (capability == CapabilityEnergy.ENERGY && energyInventory != null)
			return true;
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && fluidInventory != null)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	public int getCostMultiplier() {
		return costMultiplier;
	}

	@Override
	public ItemStackHandler getItemInventory() {
		return itemInventory;
	}

	@Override
	public ItemStackHandler getUpgradeInventory() {
		return upgradeInventory;
	}

	@Override
	public ItemStackHandler getChargeInventory() {
		return chargeInventory;
	}

	@Override
	public EnergyHandler getEnergyInventory() {
		return energyInventory;
	}

	@Override
	public FluidHandler getFluidInventory() {
		return fluidInventory;
	}

	@Override
	public ProviderType getProviderType() {
		return ProviderType.MACHINE;
	}

	@Override
	public GuiTabBlueprint getCurrentTab() {
		if (currentTab == null) {
			currentTab = getGuiTabBlueprints().get(0);
		}
		return currentTab;
	}

	@Override
	public void setCurrentTab(GuiTabBlueprint currentTab) {
		this.currentTab = currentTab;
	}

	@Override
	public void setCurrentTab(int tabId) {
		this.setCurrentTab(getGuiTabBlueprints().get(tabId));
	}

	@Override
	public MechContainer getContainer(GuiTabBlueprint blueprint, EntityPlayer player) {
		return new MechContainer(this, blueprint, player);
	}

	@Override
	public String getNameToDisplay() {
		return block.getLocalizedName();
	}

	@Override
	public List<GuiTabBlueprint> getGuiTabBlueprints() {
		List<GuiTabBlueprint> blueprints = new ArrayList<>();
		blueprints.add(mainBlueprint.makeTabBlueprint("main", new Sprite(new ItemStack(block))));
		if (hasUpgradeInventory) {
			GuiTabBlueprint upgradesTab = new GuiTabBlueprint(this, "upgrades", Sprite.UPGRADE_ICON);
			for (int i = 0; i < upgradeInventory.getSlots(); i++) {

			}
		}
		return blueprints;
	}
}
