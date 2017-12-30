package mechconstruct.blockentities;

import mechconstruct.block.BlockMachine;
import mechconstruct.gui.MechContainer;
import mechconstruct.gui.blueprint.*;
import mechconstruct.gui.blueprint.elements.*;
import mechconstruct.util.EnergyHandler;
import mechconstruct.util.EnergyUtils;
import mechconstruct.util.FluidHandler;
import mechconstruct.util.MachineSide;
import mechconstruct.util.slotconfig.DividedIOItemHandler;
import mechconstruct.util.slotconfig.SidedConfigData;
import mechconstruct.util.slotconfig.SlotCompound;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public abstract class BlockEntityMachine extends TileEntity implements ITickable, IBlueprintProvider {
	protected SidedConfigData sideConfigData;
	protected ItemStackHandler itemInventory;
	protected ItemStackHandler upgradeInventory;
	protected EnergyHandler energyInventory;
	protected FluidHandler fluidInventory;
	protected boolean hasItemInventory = false;
	protected boolean hasUpgradeInventory = false;
	protected boolean hasEnergyInventory = false;
	protected boolean hasFluidInventory = false;
	protected int costMultiplier = 0;
	protected BlockMachine block;
	protected GuiBlueprint mainBlueprint;
	protected GuiTabBlueprint currentTab;
	protected List<GuiTabBlueprint> blueprints = new ArrayList<>();
	protected List<GuiTabBlueprint> additionalBlueprints = new ArrayList<>();
	protected HashMap<EnumFacing, DividedIOItemHandler> sideHandlers;

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
			this.hasEnergyInventory = true;
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
			sideConfigData = new SidedConfigData(compound.getCompoundTag("side_config_data"));
		}
		if (hasUpgradeInventory) {
			upgradeInventory.deserializeNBT(compound.getCompoundTag("upgrade_inventory"));
		}
		if (hasEnergyInventory) {
			energyInventory.deserializeNBT(compound.getCompoundTag("energy_inventory"));
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
			compound.setTag("side_config_data", getSideConfigData().serializeNBT());
		}
		if (hasUpgradeInventory) {
			compound.setTag("upgrade_inventory", upgradeInventory.serializeNBT());
		}
		if (hasEnergyInventory) {
			compound.setTag("energy_inventory", energyInventory.serializeNBT());
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
			EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemInventory != null) {
			DividedIOItemHandler sideyboi = getHandlerForSide(side);
			return (T) sideyboi;
		}
		if (capability == CapabilityEnergy.ENERGY && energyInventory != null)
			return (T) energyInventory;
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && fluidInventory != null)
			return (T) fluidInventory;
		return super.getCapability(capability, side);
	}

	@Override
	public boolean hasCapability(
		@Nonnull
			Capability<?> capability,
		@Nullable
			EnumFacing side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemInventory != null) {
			return !getHandlerForSide(side).slots.isEmpty();
		}
		if (capability == CapabilityEnergy.ENERGY && energyInventory != null)
			return true;
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && fluidInventory != null) {
			return true;
		}
		return super.hasCapability(capability, side);
	}

	public DividedIOItemHandler getHandlerForSide(EnumFacing facing) {
		if (sideHandlers == null) {
			sideHandlers = new HashMap<>();
		}
		for (EnumFacing enumFacing : EnumFacing.values()) {
			if (sideHandlers.get(enumFacing) == null || !sideHandlers.get(enumFacing).slots.equals(getSideConfigData().getSlotsForSide(MachineSide.getMachineSide(this, facing)))) {
				sideHandlers.put(facing, new DividedIOItemHandler(getItemInventory(), getSideConfigData().getSlotsForSide(MachineSide.getMachineSide(this, facing))));
			}
			return sideHandlers.get(facing);
		}
		return new DividedIOItemHandler(itemInventory, new ArrayList<>());
	}

	public void updateSideSlot(EnumFacing facing, SlotCompound slot) {
		getSideConfigData().updateSideSlot(MachineSide.getMachineSide(this, facing), slot);
	}

	public void updateSideSlot(MachineSide side, SlotCompound slot) {
		getSideConfigData().updateSideSlot(side, slot);
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

	public boolean hasItemInventory() {
		return hasItemInventory;
	}

	public boolean hasUpgradeInventory() {
		return hasUpgradeInventory;
	}

	public boolean hasEnergyInventory() {
		return hasEnergyInventory;
	}

	public boolean hasFluidInventory() {
		return hasFluidInventory;
	}

	public SidedConfigData getSideConfigData() {
		if (sideConfigData == null) {
			this.sideConfigData = new SidedConfigData(mainBlueprint.defaultConfigs);
		}
		return sideConfigData;
	}

	public void setSideConfigData(SidedConfigData sideConfigData) {
		this.sideConfigData = sideConfigData;
	}

	public List<SlotCompound> getSlotsForSide(EnumFacing side) {
		return getSideConfigData().getSlotsForSide(MachineSide.getMachineSide(this, side));
	}

	@Override
	public ItemStackHandler getUpgradeInventory() {
		return upgradeInventory;
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

	public EnumFacing getFacing() {
		return world.getBlockState(pos).getValue(BlockMachine.FACING);
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
	public final List<GuiTabBlueprint> getGuiTabBlueprints() {
		if (blueprints.isEmpty()) {
			List<Element> universalElements = new ArrayList<>();
			List<Syncable> universalSyncables = new ArrayList<>();
			GuiTabBlueprint mainTab = mainBlueprint.makeTabBlueprint("main", new Sprite(new ItemStack(block)));
			blueprints.add(mainTab);
			blueprints.addAll(additionalBlueprints);
			if (hasEnergyInventory) {
				GuiTabBlueprint energyTab = new GuiTabBlueprint(this, "energy", new SpriteContainer().addSprite(Sprite.ENERGY_ICON_EMPTY, 8, 6).addSprite((provider) -> {
					int iconHeight = Sprite.ENERGY_ICON.height;
					int energy = provider.getEnergyInventory().getEnergy();
					int capacity = provider.getEnergyInventory().getCapacity();
					int draw = (int) ((double) energy / (double) capacity * iconHeight);
					if (energy > capacity) {
						draw = (int) ((double) capacity / capacity * iconHeight);
					}
					return new Sprite(Sprite.ENERGY_ICON.textureLocation, Sprite.ENERGY_ICON.x, Sprite.ENERGY_ICON.y + iconHeight - draw, Sprite.ENERGY_ICON.width, draw).setOffsetX(8).setOffsetY(6 + iconHeight - draw);
				}));
				energyTab.addElement(new EnergyBarElement(81, 27));
				energyTab.syncIntegerValue(() -> energyInventory.getEnergy(), energyInventory::setEnergy);
				energyTab.syncIntegerValue(() -> energyInventory.getCapacity(), energyInventory::setCapacity);
				energyTab.syncIntegerValue(() -> energyInventory.getMaxInput(), energyInventory::setMaxInput);
				energyTab.syncIntegerValue(() -> energyInventory.getMaxOutput(), energyInventory::setMaxOutput);
				energyTab.addElement(new TextElement("container.inventory", true, 4210752, 8, 83));
				energyTab.setPlayerInvPos(7, 93);
				blueprints.add(energyTab);
			}
			if (hasUpgradeInventory) {
				GuiTabBlueprint upgradesTab = new GuiTabBlueprint(this, "upgrades", Sprite.UPGRADE_ICON);
				int startX = 7;
				int startY = 19;
				int rows = 3;
				int columns = (int) Math.ceil(upgradeInventory.getSlots() / (double) rows);
				int slotNum = 0;
				for (int column = 0; column < columns; column++) {
					for (int row = 0; row < rows; row++) {
						if (slotNum < upgradeInventory.getSlots()) {
							upgradesTab.addSlot(upgradeInventory, startX + (column * 20), startY + (row * 20));
							slotNum++;
						} else {
							break;
						}
					}
				}
				upgradesTab.addElement(new TextElement("container.inventory", true, 4210752, 8, 83));
				upgradesTab.setPlayerInvPos(7, 93);
				blueprints.add(upgradesTab);
			}
			if (hasItemInventory || hasEnergyInventory || hasFluidInventory) {
				GuiTabBlueprint configureTab = new GuiTabBlueprint(this, "configure", Sprite.CONFIGURE_ICON);
				configureTab.addElements(mainTab.getElements());
				List<Element> newElements = new ArrayList<>();
				Iterator iterator = configureTab.getElements().iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					if (element instanceof DummySlotElement) {
						newElements.add(new FakeSlot(element.x, element.y));
						iterator.remove();
					}
					if (element instanceof SlotElement) {
						newElements.add(new ConfigSlotElement(((SlotElement) element).getSlotInventory(), ((SlotElement) element).getSlotId(), ((SlotElement) element).getType(), element.getX(), element.getY()));
						iterator.remove();
					}
				}
				configureTab.addElements(newElements);
				blueprints.add(configureTab);
			}
			universalElements.add(new TopEnergyBarElement(3, -3));
			universalSyncables.add(new Syncable(() -> energyInventory.getEnergy(), energyInventory::setEnergy));
			universalSyncables.add(new Syncable(() -> energyInventory.getCapacity(), energyInventory::setCapacity));

			universalElements.addAll(getUniversalElements());
			universalSyncables.addAll(getUniversalSyncables());
			for (GuiTabBlueprint blueprint : blueprints) {
				for (Syncable syncable : universalSyncables) {
					if (syncable.isShort) {
						blueprint.syncShortValue(syncable.getIntSupplier(), syncable.getIntConsumer());
					} else {
						blueprint.syncIntegerValue(syncable.getIntSupplier(), syncable.getIntConsumer());
					}
				}
				for (Element element : universalElements) {
					blueprint.addElement(element);
				}
			}
		}
		return blueprints;
	}

	public List<Element> getUniversalElements() {
		return new ArrayList<>();
	}

	public List<Syncable> getUniversalSyncables() {
		return new ArrayList<>();
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	public class Syncable {
		private IntSupplier intSupplier;
		private IntConsumer intConsumer;
		private boolean isShort;

		public Syncable(IntSupplier intSupplier, IntConsumer intConsumer) {
			this(intSupplier, intConsumer, false);
		}

		public Syncable(IntSupplier intSupplier, IntConsumer intConsumer, boolean isShort) {
			this.intSupplier = intSupplier;
			this.intConsumer = intConsumer;
			this.isShort = isShort;
		}

		public IntSupplier getIntSupplier() {
			return intSupplier;
		}

		public IntConsumer getIntConsumer() {
			return intConsumer;
		}

		public boolean isShort() {
			return isShort;
		}
	}

}
