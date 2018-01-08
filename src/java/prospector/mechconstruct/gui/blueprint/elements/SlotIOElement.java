package prospector.mechconstruct.gui.blueprint.elements;

import prospector.mechconstruct.blockentities.BlockEntityMachine;
import prospector.mechconstruct.util.slotconfig.SlotCompound;
import prospector.mechconstruct.gui.MechGui;
import prospector.mechconstruct.gui.blueprint.Sprite;
import prospector.mechconstruct.gui.blueprint.SpriteContainer;
import prospector.mechconstruct.network.MechPacketManager;
import prospector.mechconstruct.network.PacketSlotSave;
import prospector.mechconstruct.util.slotconfig.SlotConfig;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotIOElement extends Element {
	protected int id;
	protected EnumFacing side;
	protected boolean isFirst = true;

	public SlotIOElement(int x, int y, int slotId, EnumFacing side) {
		super(x, y, new SpriteContainer().addSprite(Sprite.EMPTY));
		this.id = slotId;
		this.side = side;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(MechGui gui) {
		super.draw(gui);
		SlotCompound currentSlot = null;
		for (SlotCompound compound : ((BlockEntityMachine) gui.provider).getSlotsForSide(side)) {
			if (compound.getId() == id) {
				currentSlot = compound;
				break;
			}
		}
		if (currentSlot == null) {
			currentSlot = new SlotCompound(id, SlotConfig.NONE);
		}
		SlotConfig currentConfig = currentSlot.getSlotConfig();
		if (currentConfig.isInsert()) {
			container.setSprite(0, Sprite.INPUT_ICON);
		} else if (currentConfig.isExtact()) {
			container.setSprite(0, Sprite.OUTPUT_ICON);
		} else {
			container.setSprite(0, Sprite.EMPTY);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void clientCalls() {
		super.clientCalls();
		setWidth(16);
		setHeight(16);
		addPressAction((element, gui, provider, mouseX, mouseY) -> {
			cyleSlotConfig(side, gui);
		});
	}

	@SideOnly(Side.CLIENT)
	public void cyleSlotConfig(EnumFacing side, MechGui gui) {
		SlotCompound currentSlot = null;
		for (SlotCompound compound : ((BlockEntityMachine) gui.provider).getSlotsForSide(side)) {
			if (compound.getId() == id) {
				currentSlot = compound;
				break;
			}
		}
		if (currentSlot == null) {
			currentSlot = new SlotCompound(id, SlotConfig.NONE);
		}
		SlotConfig currentConfig = currentSlot.getSlotConfig();
		SlotConfig newConfig = currentConfig.getNext();
		PacketSlotSave packetSlotSave = new PacketSlotSave(((BlockEntityMachine) gui.provider).getPos(), side, currentSlot.setSlotConfig(newConfig));
		MechPacketManager.networkWrapper.sendToServer(packetSlotSave);
	}
}
