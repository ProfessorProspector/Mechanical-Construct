package mechconstruct.gui;

import mechconstruct.gui.blueprint.GuiTabBlueprint;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.gui.blueprint.elements.ElementBase;
import mechconstruct.proxy.MechClient;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class MechGui extends GuiContainer implements IDynamicAdjustmentGUI {
	public final GuiTabBlueprint blueprint;
	public final IBlueprintProvider provider;
	public int xFactor;
	public int yFactor;
	public MechContainer container = (MechContainer) inventorySlots;
	public ArrayList<ButtonElement> buttons = new ArrayList<>();

	public MechGui(GuiTabBlueprint blueprint, EntityPlayer player) {
		super(blueprint.provider.getContainer(blueprint.provider, blueprint, player));
		this.blueprint = blueprint;
		this.provider = blueprint.provider;
		xSize = blueprint.xSize;
		ySize = blueprint.ySize;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttons.clear();
		for (GuiTabBlueprint tab : provider.getGuiTabBlueprints()) {
			Sprite sprite = Sprite.LEFT_TAB;
			int x = -23;
			int width = 23;
			if (provider.getCurrentTab().equals(tab)) {
				x = -26;
				width = 29;
				sprite = Sprite.LEFT_TAB_SELECTED;
			}
			tab.button = new ButtonElement(x, 5 + provider.getGuiTabBlueprints().indexOf(tab) * (26 + 1), width, 26,
				new SpriteContainer(sprite),
				new SpriteContainer(tab.getSprite(), 5, 5));
			tab.button.addPressAction(tab.buttonAction);
			buttons.add(tab.button);
		}
		buttons.addAll(blueprint.buttonElements);
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
		MechClient.GUI_ASSEMBLER.drawDefaultBackground(this, 0, 0, xSize, ySize);
		for (ElementBase e : blueprint.elements) {
			e.draw(this);
		}
		for (ButtonElement button : buttons) {
			if (MechClient.GUI_ASSEMBLER.isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
				button.isHovering = true;
				button.onHover(provider, this, mouseX, mouseY);
			} else {
				button.isHovering = false;
			}
			button.update();
			button.draw(this);
			for (SpriteContainer spriteContainer : button.spriteContainers) {
				MechClient.GUI_ASSEMBLER.drawSprite(this, spriteContainer.sprite, button.x + spriteContainer.xOffset, button.y + spriteContainer.yOffset);
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
		MechClient.GUI_ASSEMBLER.drawCenteredString(this, provider.getNameToDisplay() + ": " + provider.getCurrentTab().getLocalizedName(), 6, 4210752);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		xFactor = guiLeft;
		yFactor = guiTop;
		if (mouseButton == 0) {
			for (ButtonElement button : buttons) {
				if (MechClient.GUI_ASSEMBLER.isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
					button.isPressing = true;
					button.onStartPress(provider, this, mouseX, mouseY);
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
				if (MechClient.GUI_ASSEMBLER.isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
					button.isDragging = true;
					button.onDrag(provider, this, mouseX, mouseY);
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
				if (MechClient.GUI_ASSEMBLER.isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
					button.isReleasing = true;
					button.onRelease(provider, this, mouseX, mouseY);
				} else {
					button.isReleasing = false;
				}
				button.isPressing = false;
			}
		}
	}
}
