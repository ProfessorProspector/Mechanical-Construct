package prospector.mechconstruct.gui.blueprint.elements;

import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.blockentities.BlockEntityMachine;
import prospector.mechconstruct.gui.MechGui;
import prospector.mechconstruct.gui.SlotType;
import prospector.mechconstruct.gui.blueprint.Sprite;
import prospector.mechconstruct.util.MachineSide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ConfigSlotElement extends Element {
	SlotType type;
	ItemStackHandler inventory;
	int id;
	List<Element> toRemove;

	public ConfigSlotElement(ItemStackHandler slotInventory, int slotId, SlotType type, int x, int y) {
		super(x, y, type.getButtonSprite());
		this.type = type;
		this.inventory = slotInventory;
		this.id = slotId;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void clientCalls() {
		super.clientCalls();
		addPressAction(((element, gui, provider, mouseX, mouseY) -> {
			List<Element> elements = new ArrayList<>();
			elements.add(new Element(0 - gui.getOffsetFactorX(), 0 - gui.getOffsetFactorY(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight) {
				@Override
				@SideOnly(Side.CLIENT)
				public void draw(MechGui gui) {
					super.draw(gui);
					MechConstruct.proxy.getGuiAssembler().drawRect(gui, x, y, getWidth(gui.provider), getHeight(gui.provider), 0xA0000000);
				}
			}.addPressAction((element1, gui1, provider1, mouseX1, mouseY1) -> {
				if (((ConfigSlotElement) element).toRemove != null) {
					gui.elements.removeAll(((ConfigSlotElement) element).toRemove);
				}
			}));
			elements.add(new SlotConfigPopupElement(((ConfigSlotElement) element).id, x - 22, y - 22));
			elements.add(new ButtonElement(x + 44, y - 28, Sprite.EXIT_BUTTON)
				.addPressAction((element1, gui1, provider1, mouseX1, mouseY1) -> {
					if (((ConfigSlotElement) element).toRemove != null) {
						gui.elements.removeAll(((ConfigSlotElement) element).toRemove);
					}
				}));
			//			elements.add(new CheckBoxElement(I18n.format("gui.mechconstruct.auto_input"), 4210752, x - 29, y + 40, true, Sprite.DARK_CHECK_BOX).addPressAction((element1, gui1, provider1, mouseX1, mouseY1) -> {
			//				updateCheckBox((CheckBoxElement) element1, "input", gui1);
			//			}));
			//			elements.add(new CheckBoxElement(I18n.format("gui.mechconstruct.auto_output"), 4210752, x - 29, y + 55, true, Sprite.DARK_CHECK_BOX).addPressAction((element1, gui1, provider1, mouseX1, mouseY1) -> {
			//				updateCheckBox((CheckBoxElement) element1, "output", gui1);
			//			}));
			elements.add(new SlotIOElement(x - 19, y + 1, ((ConfigSlotElement) element).id, MachineSide.LEFT.getFacing((BlockEntityMachine) provider)));
			elements.add(new SlotIOElement(x, y - 18, ((ConfigSlotElement) element).id, MachineSide.TOP.getFacing((BlockEntityMachine) provider)));
			elements.add(new SlotIOElement(x, y + 1, ((ConfigSlotElement) element).id, MachineSide.FRONT.getFacing((BlockEntityMachine) provider)));
			elements.add(new SlotIOElement(x, y + 20, ((ConfigSlotElement) element).id, MachineSide.BOTTOM.getFacing((BlockEntityMachine) provider)));
			elements.add(new SlotIOElement(x + 19, y + 1, ((ConfigSlotElement) element).id, MachineSide.RIGHT.getFacing((BlockEntityMachine) provider)));
			elements.add(new SlotIOElement(x + 19, y + 20, ((ConfigSlotElement) element).id, MachineSide.BACK.getFacing((BlockEntityMachine) provider)));
			gui.addAdditionalElements(elements);
			((ConfigSlotElement) element).toRemove = elements;
		}));
	}

	@SideOnly(Side.CLIENT)
	public void updateCheckBox(CheckBoxElement checkBoxElement, String type, MechGui gui) {
/*		SlotConfiguration.SlotConfigHolder configHolder = ((BlockEntityMachine) gui.provider).slotConfiguration.getSlotDetails(id);
		boolean input = configHolder.autoInput();
		boolean output = configHolder.autoOutput();
		if (type.equalsIgnoreCase("input")) {
			input = !configHolder.autoInput();
		}
		if (type.equalsIgnoreCase("output")) {
			output = !configHolder.autoOutput();
		}

		PacketIOSave packetSlotSave = new PacketIOSave(((BlockEntityMachine) gui.provider).getPos(), id, input, output);
		MechPacketManager.networkWrapper.sendToServer(packetSlotSave);*/
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawTooltip(MechGui gui, int mouseX, int mouseY) {
		super.drawTooltip(gui, mouseX, mouseY);
		MechConstruct.proxy.getGuiAssembler().drawSimpleTooltip(gui, mouseX, mouseY, I18n.format("gui.mechconstruct.edit_slot_config"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(MechGui gui) {
		super.draw(gui);
		ItemStack stack = inventory.getStackInSlot(id);
		int xPos = x + 1 + gui.getOffsetFactorX();
		int yPos = y + 1 + gui.getOffsetFactorY();

		GlStateManager.enableDepth();
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		gui.renderItem.renderItemAndEffectIntoGUI(gui.mc.player, stack, xPos, yPos);
		gui.renderItem.renderItemOverlayIntoGUI(gui.mc.fontRenderer, stack, xPos, yPos, null);
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
		if (isHovering) {
			MechConstruct.proxy.getGuiAssembler().drawSprite(gui, type.getButtonHoverOverlay(), x, y);
		}
	}

	public SlotType getType() {
		return type;
	}
}