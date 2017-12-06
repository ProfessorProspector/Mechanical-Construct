package mechconstruct.gui;

import mechconstruct.MechConstruct;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.gui.blueprint.elements.ElementBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class GuiMachine extends GuiContainer implements IDynamicAdjustmentGUI {
	public final GuiBlueprint blueprint;
	public int xFactor;
	public int yFactor;
	public ContainerMachine container = (ContainerMachine) inventorySlots;
	public ArrayList<ButtonElement> buttons = new ArrayList<>();

	public GuiMachine(GuiBlueprint blueprint, EntityPlayer player) {
		super(blueprint.machine.getContainer(player));
		this.blueprint = blueprint;
		xSize = blueprint.xSize;
		ySize = blueprint.ySize;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttons.addAll(blueprint.buttonElements);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		xFactor = 0;
		yFactor = 0;
		drawTitle();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		xFactor = guiLeft;
		yFactor = guiTop;
		MechConstruct.proxy.getGuiAssembler().drawDefaultBackground(this, 0, 0, xSize, ySize);
		for (ElementBase e : blueprint.elements) {
			e.draw(this);
		}
		for (ButtonElement button : buttons) {
			if (MechConstruct.proxy.getGuiAssembler().isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
				button.isHovering = true;
				button.onHover(container.machine, mouseX, mouseY);
			} else {
				button.isHovering = false;
			}
			button.update();
			button.draw(this);
			for (SpriteContainer spriteContainer : button.spriteContainers) {
				MechConstruct.proxy.getGuiAssembler().drawSprite(this, spriteContainer.sprite, button.x + spriteContainer.xOffset, button.y + spriteContainer.yOffset);
			}
		}
	}

	@Override
	public int getOffsetFactorX() {
		return xFactor;
	}

	@Override
	public int getOffsetFactorY() {
		return yFactor;
	}

	protected void drawTitle() {
		MechConstruct.proxy.getGuiAssembler().drawCenteredString(this, I18n.format(blueprint.machine.getBlockType().getUnlocalizedName() + ".name"), 6, 4210752);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		xFactor = guiLeft;
		yFactor = guiTop;
		if (mouseButton == 0) {
			for (ButtonElement button : buttons) {
				if (MechConstruct.proxy.getGuiAssembler().isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
					button.isPressing = true;
					button.onStartPress(container.machine, mouseX, mouseY);
				} else {
					button.isPressing = false;
				}
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastClick);
		xFactor = guiLeft;
		yFactor = guiTop;
		if (mouseButton == 0) {
			for (ButtonElement button : buttons) {
				if (MechConstruct.proxy.getGuiAssembler().isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
					button.isDragging = true;
					button.onDrag(container.machine, mouseX, mouseY);
				} else {
					button.isDragging = false;
				}
			}
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		super.mouseReleased(mouseX, mouseY, mouseButton);
		xFactor = guiLeft;
		yFactor = guiTop;
		if (mouseButton == 0) {
			for (ButtonElement button : buttons) {
				if (MechConstruct.proxy.getGuiAssembler().isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
					button.isReleasing = true;
					button.onRelease(container.machine, mouseX, mouseY);
				} else {
					button.isReleasing = false;
				}
				button.isPressing = false;
			}
		}
	}

}
