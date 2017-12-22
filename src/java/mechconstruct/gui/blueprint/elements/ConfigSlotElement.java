package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.Sprite;
import mechconstruct.proxy.MechClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ConfigSlotElement extends ElementBase {
	SlotType type;
	ItemStackHandler inventory;
	int id;
	List<ElementBase> toRemove;

	public ConfigSlotElement(ItemStackHandler slotInventory, int slotId, SlotType type, int x, int y) {
		super(x, y, type.getButtonSprite());
		this.type = type;
		this.inventory = slotInventory;
		this.id = slotId;
		addPressAction(((element, gui, provider, mouseX, mouseY) -> {
			List<ElementBase> elements = new ArrayList<>();
			elements.add(new ElementBase(0 - gui.getOffsetFactorX(), 0 - gui.getOffsetFactorY(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight) {
				@Override
				public void draw(MechGui gui) {
					super.draw(gui);
					MechClient.GUI_ASSEMBLER.drawRect(gui, x, y, getWidth(gui.provider), getHeight(gui.provider), 0xA0000000);
				}
			}.addPressAction((element1, gui1, provider1, mouseX1, mouseY1) -> {
				if (((ConfigSlotElement) element).toRemove != null) {
					gui.elements.removeAll(((ConfigSlotElement) element).toRemove);
				}
			}));
			elements.add(new SlotConfigPopupElement(((ConfigSlotElement) element).id, x - 22, y - 22));
			elements.add(new ButtonElement(x + 29, y - 25, Sprite.EXIT_BUTTON)
				.addPressAction((element1, gui1, provider1, mouseX1, mouseY1) -> {
					if (((ConfigSlotElement) element).toRemove != null) {
						gui.elements.removeAll(((ConfigSlotElement) element).toRemove);
					}
				}));
			elements.add(new CheckBoxElement("Auto Input", 0xFFFFFFFF, x - 26, y + 42, false, Sprite.LIGHT_CHECK_BOX));
			elements.add(new CheckBoxElement("Auto Output", 0xFFFFFFFF, x - 26, y + 57, true, Sprite.LIGHT_CHECK_BOX));
			gui.elements.addAll(elements);
			((ConfigSlotElement) element).toRemove = elements;
		}));
	}

	@Override
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
			MechClient.GUI_ASSEMBLER.drawSprite(gui, type.getButtonHoverOverlay(), x, y);
		}
	}

	public SlotType getType() {
		return type;
	}
}