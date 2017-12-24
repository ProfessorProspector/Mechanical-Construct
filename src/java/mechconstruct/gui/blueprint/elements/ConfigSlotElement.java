package mechconstruct.gui.blueprint.elements;

import mechconstruct.MechConstruct;
import mechconstruct.gui.MechGui;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.blueprint.Sprite;
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

			elements.add(new CheckBoxElement(I18n.format("gui.mechconstruct.auto_input"), 4210752, x - 29, y + 40, false, Sprite.DARK_CHECK_BOX));
			elements.add(new CheckBoxElement(I18n.format("gui.mechconstruct.auto_output"), 4210752, x - 29, y + 55, true, Sprite.DARK_CHECK_BOX));
			gui.addAdditionalElements(elements);
			((ConfigSlotElement) element).toRemove = elements;
		}));
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