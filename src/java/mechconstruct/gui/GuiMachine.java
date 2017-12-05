package mechconstruct.gui;

import mechconstruct.MechConstruct;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.gui.blueprint.elements.ElementBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class GuiMachine extends GuiContainer implements IDynamicAdjustmentGUI {
	public final GuiBlueprint blueprint;
	public int xFactor;
	public int yFactor;
	public ContainerMachine container = (ContainerMachine) inventorySlots;

	public GuiMachine(GuiBlueprint blueprint, EntityPlayer player) {
		super(blueprint.machine.getContainer(player));
		this.blueprint = blueprint;
		xSize = blueprint.xSize;
		ySize = blueprint.ySize;
	}

	@Override
	public void initGui() {
		super.initGui();
		int buttonId = 0;
		for (ButtonElement element : blueprint.buttonElements) {
			this.buttonList.add(new MechGuiButton(buttonId, element));
			buttonId++;
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
		MechConstruct.proxy.getGuiAssembler().drawDefaultBackground(this, 0, 0, xSize, ySize);
		for (ElementBase e : blueprint.elements) {
			e.draw(this);
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
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button instanceof MechGuiButton) {
			((MechGuiButton) button).element.getAction().execute(this.container.machine);
		}
		super.actionPerformed(button);
	}
}
