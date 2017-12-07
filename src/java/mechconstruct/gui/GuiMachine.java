package mechconstruct.gui;

import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.gui.blueprint.elements.ElementBase;
import mechconstruct.proxy.MechClient;
import mechconstruct.util.RedstoneMode;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import java.io.IOException;
import java.util.ArrayList;

public class GuiMachine extends GuiContainer implements IDynamicAdjustmentGUI {
	public final GuiBlueprint blueprint;
	public final GuiTab mainTab;
	public final GuiTab energyTab;
	public final GuiTab redstoneTab;
	public final GuiTab upgradesTab;
	public final GuiTab configureTab;
	public final GuiTab jeiTab;
	public int xFactor;
	public int yFactor;
	public ContainerMachine container = (ContainerMachine) inventorySlots;
	public ArrayList<ButtonElement> buttons = new ArrayList<>();
	public ArrayList<GuiTab> tabs = new ArrayList<>();
	public RedstoneMode redstoneMode = RedstoneMode.LOW;
	public GuiTab currentTab = null;

	public GuiMachine(GuiBlueprint blueprint, EntityPlayer player) {
		super(blueprint.machine.getContainer(player));
		this.blueprint = blueprint;
		xSize = blueprint.xSize;
		ySize = blueprint.ySize;
		mainTab = new GuiTab("main", new Sprite(new ItemStack(blueprint.machine.getBlock())));
		energyTab = new GuiTab("energy", Sprite.ENERGY_ICON);
		redstoneTab = new GuiTab("redstone", () -> {
			switch (redstoneMode) {
				case LOW:
					return Sprite.REDSTONE_LOW_ICON;
				case HIGH:
					return Sprite.REDSTONE_HIGH_ICON;
				case DISABLED:
					return Sprite.REDSTONE_DISABLED_ICON;
			}
			return null;
		});
		upgradesTab = new GuiTab("upgrades", Sprite.UPGRADE_ICON);
		configureTab = new GuiTab("configure", Sprite.CONFIGURE_ICON);
		jeiTab = new GuiTab("jei", Sprite.JEI_ICON, ((element, gui, machine, mouseX, mouseY) -> {

		}));
		if (blueprint.hasMainTab)
			tabs.add(mainTab);
		if (blueprint.machine.getEnergyInventory() != null)
			tabs.add(energyTab);
		if (blueprint.hasRedstoneControls)
			tabs.add(redstoneTab);
		if (blueprint.machine.getUpgradeInventory() != null)
			tabs.add(upgradesTab);
		if (blueprint.machine.getItemInventory() != null || blueprint.machine.getEnergyInventory() != null || blueprint.machine.getFluidInventory() != null)
			tabs.add(configureTab);
		if (blueprint.hasJeiCategory && Loader.isModLoaded("jei"))
			tabs.add(jeiTab);
		currentTab = tabs.get(0);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttons.clear();
		for (GuiTab tab : tabs) {
			Sprite sprite = Sprite.LEFT_TAB;
			int x = -23;
			int width = 23;
			if (currentTab.equals(tab)) {
				x = -26;
				width = 29;
				sprite = Sprite.LEFT_TAB_SELECTED;
			}
			tab.button = new ButtonElement(x, 5 + tabs.indexOf(tab) * (26 + 1), width, 26,
				new SpriteContainer(sprite),
				new SpriteContainer(tab.getSprite(), 5, 5));
			tab.button.addPressAction(tab.getButtonAction());
			buttons.add(tab.button);
		}
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
		if (currentTab == mainTab) {
			this.buttons.addAll(blueprint.buttonElements);
			for (ElementBase e : blueprint.elements) {
				e.draw(this);
			}
		}
		for (ButtonElement button : buttons) {
			if (MechClient.GUI_ASSEMBLER.isInRect(this, button.x, button.y, button.width, button.height, mouseX, mouseY)) {
				button.isHovering = true;
				button.onHover(container.machine, this, mouseX, mouseY);
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
		MechClient.GUI_ASSEMBLER.drawCenteredString(this, I18n.format(blueprint.machine.getBlockType().getUnlocalizedName() + ".name") + ": " + currentTab.getLocalizedName(), 6, 4210752);
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
					button.onStartPress(container.machine, this, mouseX, mouseY);
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
					button.onDrag(container.machine, this, mouseX, mouseY);
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
					button.onRelease(container.machine, this, mouseX, mouseY);
				} else {
					button.isReleasing = false;
				}
				button.isPressing = false;
			}
		}
	}
}
