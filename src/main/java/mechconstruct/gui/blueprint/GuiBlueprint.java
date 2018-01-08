package mechconstruct.gui.blueprint;

import com.google.common.base.Preconditions;
import com.mojang.realmsclient.util.Pair;
import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.elements.DummySlotElement;
import mechconstruct.gui.blueprint.elements.Element;
import mechconstruct.gui.blueprint.elements.SlotElement;
import mechconstruct.gui.slot.MechSlot;
import mechconstruct.util.slotconfig.SlotConfig;
import mechconstruct.util.slotconfig.SlotSideMap;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class GuiBlueprint {
	public final List<Pair<IntSupplier, IntConsumer>> shortSyncables = new ArrayList<>();
	public final List<Pair<IntSupplier, IntConsumer>> intSyncables = new ArrayList<>();
	public IBlueprintProvider provider;
	public List<SlotElement> slots = new ArrayList<>();
	public int xSize = 0;
	public int ySize = 0;
	public int playerInvX = -1;
	public int playerInvY = -1;
	public List<SlotMapCompound> defaultConfigs = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();

	public GuiBlueprint(IBlueprintProvider provider) {
		this.provider = provider;
		setSize(176, 176);
	}

	public GuiBlueprint setSize(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		return this;
	}

	/**
	 * @param x the player's inventory slots will start drawing at this x position (this is the top left corner) [-1 to disable]
	 * @param y the player's inventory slots will start drawing at this y position (this is the top left corner) [-1 to disable]
	 * @return returns the blueprint for fast building of blueprints
	 */
	public GuiBlueprint setPlayerInvPos(int x, int y) {
		this.playerInvX = x;
		this.playerInvY = y;
		if (x > -1 && y > -1) {
			for (int row = 0; row < 3; ++row) {
				for (int column = 0; column < 9; ++column) {
					int index = column + row * 9 + 9;
					int xpos = x + (column * 18);
					int ypos = y + (row * 18);
					this.elements.add(new DummySlotElement(SlotType.NORMAL, xpos, ypos));
				}
			}
			for (int column = 0; column < 9; ++column) {
				int xpos = x + (column * 18);
				int ypos = y + 58;
				this.elements.add(new DummySlotElement(SlotType.NORMAL, xpos, ypos));
			}
		}
		return this;
	}

	public GuiBlueprint addElement(Element element) {
		MechConstruct.proxy.clientCalls(element);
		this.elements.add(element);
		return this;
	}

	public void addElements(Collection<? extends Element> elements) {
		for (Element element : elements) {
			MechConstruct.proxy.clientCalls(element);
		}
		this.elements.addAll(elements);
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		for (Element element : elements) {
			MechConstruct.proxy.clientCalls(element);
		}
		this.elements = elements;
	}

	public GuiBlueprint addSlot(SlotElement slot) {
		this.slots.add(slot);
		this.elements.add(slot);
		return this;
	}

	public GuiBlueprint addSlot(ItemStackHandler inventory, SlotType type, int x, int y) {
		addSlot(new SlotElement(inventory, slots.size(), x + type.getSlotOffsetX(), y + type.getSlotOffsetY(), type, x, y, (slot, stack) -> true));
		return this;
	}

	public GuiBlueprint addSlot(SlotType type, int x, int y) {
		return addSlot(provider.getItemInventory(), type, x, y);
	}

	public GuiBlueprint addSlot(int x, int y) {
		return addSlot(SlotType.NORMAL, x, y);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(SlotType type, int x, int y, SlotSideMap defaultConfig) {
		Preconditions.checkArgument(provider instanceof BlockEntityMachine, "Provider is not of type BlockEntityMachine");
		defaultConfigs.add(new SlotMapCompound(slots.size(), defaultConfig));
		return addSlot(provider.getItemInventory(), type, x, y);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(int x, int y, SlotSideMap defaultConfig) {
		return addMachineSlot(SlotType.NORMAL, x, y, defaultConfig);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(int x, int y) {
		return addMachineSlot(SlotType.NORMAL, x, y, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE));
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(SlotType type, int x, int y) {
		return addMachineSlot(type, x, y, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE));
	}

	public GuiBlueprint addSlot(ItemStackHandler inventory, int x, int y) {
		return addSlot(inventory, SlotType.NORMAL, x, y);
	}

	public GuiBlueprint addSlot(ItemStackHandler inventory, SlotType type, int x, int y, MechSlot.SlotFilter filter) {
		addSlot(new SlotElement(inventory, slots.size(), x + type.getSlotOffsetX(), y + type.getSlotOffsetY(), type, x, y, filter));
		return this;
	}

	public GuiBlueprint addSlot(SlotType type, int x, int y, MechSlot.SlotFilter filter) {
		return addSlot(provider.getItemInventory(), type, x, y, filter);
	}

	public GuiBlueprint addSlot(int x, int y, MechSlot.SlotFilter filter) {
		return addSlot(SlotType.NORMAL, x, y, filter);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(SlotType type, int x, int y, SlotSideMap defaultConfig, MechSlot.SlotFilter filter) {
		Preconditions.checkArgument(provider instanceof BlockEntityMachine, "Provider is not of type BlockEntityMachine");
		defaultConfigs.add(new SlotMapCompound(slots.size(), defaultConfig));
		return addSlot(provider.getItemInventory(), type, x, y, filter);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(int x, int y, SlotSideMap defaultConfig, MechSlot.SlotFilter filter) {
		return addMachineSlot(SlotType.NORMAL, x, y, defaultConfig, filter);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(int x, int y, MechSlot.SlotFilter filter) {
		return addMachineSlot(SlotType.NORMAL, x, y, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE), filter);
	}

	//Only work on BlockEntityMachine
	public GuiBlueprint addMachineSlot(SlotType type, int x, int y, MechSlot.SlotFilter filter) {
		return addMachineSlot(type, x, y, new SlotSideMap(SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE, SlotConfig.NONE), filter);
	}

	public GuiBlueprint addSlot(ItemStackHandler inventory, int x, int y, MechSlot.SlotFilter filter) {
		return addSlot(inventory, SlotType.NORMAL, x, y, filter);
	}

	public GuiBlueprint syncIntegerValue(final IntSupplier supplier, final IntConsumer setter) {
		this.intSyncables.add(Pair.of(supplier, setter));
		return this;
	}

	public GuiBlueprint syncShortValue(final IntSupplier supplier, final IntConsumer setter) {
		this.shortSyncables.add(Pair.of(supplier, setter));
		return this;
	}

	public GuiTabBlueprint makeTabBlueprint(String name, Sprite sprite, Element.Action additionalAction) {
		GuiTabBlueprint tabBlueprint;
		if (additionalAction == null) {
			tabBlueprint = new GuiTabBlueprint(provider, name, sprite);
		} else {
			tabBlueprint = new GuiTabBlueprint(provider, name, additionalAction, sprite);
		}
		tabBlueprint.setElements(elements);
		tabBlueprint.slots = slots;
		tabBlueprint.xSize = xSize;
		tabBlueprint.ySize = ySize;
		tabBlueprint.playerInvX = playerInvX;
		tabBlueprint.playerInvY = playerInvY;
		return tabBlueprint;
	}

	public GuiTabBlueprint makeTabBlueprint(String name, Sprite sprite) {
		return makeTabBlueprint(name, sprite, null);
	}

	public class SlotMapCompound {
		int id;
		SlotSideMap map;

		public SlotMapCompound(int id, SlotSideMap map) {
			this.id = id;
			this.map = map;
		}

		public int getId() {
			return id;
		}

		public SlotSideMap getMap() {
			return map;
		}
	}
}
