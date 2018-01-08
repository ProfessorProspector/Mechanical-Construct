package mechconstruct.gui;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.blueprint.GuiTabBlueprint;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.Sprite;
import mechconstruct.gui.blueprint.elements.Element;
import mechconstruct.network.MechPacketHandler;
import mechconstruct.network.PacketGuiTabItemStack;
import mechconstruct.network.PacketGuiTabMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MechGui extends GuiContainer implements IDynamicAdjustmentGUI {
	public final GuiTabBlueprint blueprint;
	public final IBlueprintProvider provider;
	public int xFactor;
	public int yFactor;
	public MechContainer container = (MechContainer) inventorySlots;
	public ArrayList<Element> elements = new ArrayList<>();
	public RenderItem renderItem;

	public MechGui(GuiTabBlueprint blueprint, EntityPlayer player) {
		super(blueprint.provider.getContainer(blueprint, player));
		this.blueprint = blueprint;
		this.provider = blueprint.provider;
		xSize = blueprint.xSize;
		ySize = blueprint.ySize;
	}

	public void addAdditionalElements(List<Element> elements) {
		for (Element element : elements) {
			MechConstruct.proxy.clientCalls(element);
		}
		this.elements.addAll(elements);
	}

	@Override
	public void initGui() {
		super.initGui();
		renderItem = itemRender;
		elements.clear();
		elements.addAll(blueprint.getElements());
		for (GuiTabBlueprint tab : provider.getGuiTabBlueprints()) {
			Sprite sprite = Sprite.LEFT_TAB;
			int x = -23;
			int width = 23;
			if (provider.getCurrentTab().equals(tab)) {
				x = -26;
				width = 29;
				sprite = Sprite.LEFT_TAB_SELECTED;
			}
			Element button = new Element(x, 5 + provider.getGuiTabBlueprints().indexOf(tab) * (26 + 1), width, 26, sprite) {
				public void drawTooltip(MechGui gui, int mouseX, int mouseY) {
					if (MechConstruct.proxy.getGuiAssembler().isInRect(gui, x, y, getWidth(provider), getHeight(provider), mouseX, mouseY)) {
						net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(tab.tooltipText, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRenderer);
						GlStateManager.disableLighting();
						GlStateManager.color(1, 1, 1, 1);
					}
				}
			};
			int spriteSize = button.container.offsetSprites.size();
			if (!tab.getSpriteContainer().offsetSprites.isEmpty()) {
				if (button.container.offsetSprites.size() == spriteSize) {
					button.container.offsetSprites.addAll(tab.getSpriteContainer().offsetSprites);
				} else {
					for (int i = spriteSize; i < tab.getSpriteContainer().offsetSprites.size(); i++) {
						button.container.offsetSprites.set(i, tab.getSpriteContainer().offsetSprites.get(i - spriteSize));
					}
				}
			}
			button.addUpdateAction((gui, element) -> {
				if (!tab.getSpriteContainer().offsetSprites.isEmpty()) {
					if (element.container.offsetSprites.size() == spriteSize) {
						element.container.offsetSprites.addAll(tab.getSpriteContainer().offsetSprites);
					} else {
						for (int i = spriteSize; i < tab.getSpriteContainer().offsetSprites.size(); i++) {
							element.container.offsetSprites.set(i, tab.getSpriteContainer().offsetSprites.get(i - spriteSize));
						}
					}
				}
			});
			button.addPressAction((element, gui, blueprintProvider, mouseX, mouseY) -> {
				if (blueprintProvider.getCurrentTab() != tab) {
					element.x = -26;
					element.setWidth(29);
					element.container.setSprite(0, Sprite.LEFT_TAB_SELECTED);
					blueprintProvider.setCurrentTab(tab);
					if (gui.blueprint.provider.getProviderType() == IBlueprintProvider.ProviderType.ITEM) {
						MechPacketHandler.networkWrapper.sendToServer(new PacketGuiTabItemStack(blueprintProvider.getGuiTabBlueprints().indexOf(tab)));
					} else if (gui.blueprint.provider.getProviderType() == IBlueprintProvider.ProviderType.MACHINE) {
						List<GuiTabBlueprint> list = blueprintProvider.getGuiTabBlueprints();
						MechPacketHandler.networkWrapper.sendToServer(new PacketGuiTabMachine(((BlockEntityMachine) blueprintProvider).getPos(), list.indexOf(tab)));
					}
					if (tab.getAdditionalAction() != null) {
						tab.getAdditionalAction().execute(element, gui, blueprintProvider, mouseX, mouseY);
					}
				}
			});
			elements.add(button);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		xFactor = guiLeft;
		yFactor = guiTop;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		xFactor = 0;
		yFactor = 0;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		xFactor = guiLeft;
		yFactor = guiTop;
		MechConstruct.proxy.getGuiAssembler().drawDefaultBackground(this, 0, 0, xSize, ySize);
		drawTitle();
		for (Element element : elements) {
			element.draw(this);
		}
		for (int i = elements.size() - 1; i >= 0; i--) {
			Element element = elements.get(i);
			if (MechConstruct.proxy.getGuiAssembler().isInRect(this, element.x, element.y, element.getWidth(provider), element.getHeight(provider), mouseX, mouseY)) {
				element.isHovering = true;
				element.onHover(provider, this, mouseX, mouseY);
				for (Element e : elements) {
					if (e != element) {
						e.isHovering = false;
					}
				}
				break;
			} else {
				element.isHovering = false;
			}
			element.renderUpdate(this);
		}
	}

	@Override
	public void updateScreen() {
		for (Element element : elements) {
			element.update(this);
		}
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {
		super.renderHoveredToolTip(mouseX, mouseY);
		xFactor = guiLeft;
		yFactor = guiTop;
		for (int i = elements.size() - 1; i >= 0; i--) {
			Element element = elements.get(i);
			if (MechConstruct.proxy.getGuiAssembler().isInRect(this, element.x, element.y, element.getWidth(provider), element.getHeight(provider), mouseX, mouseY)) {
				element.drawTooltip(this, mouseX, mouseY);
				break;
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
		MechConstruct.proxy.getGuiAssembler().drawCenteredString(this, provider.getNameToDisplay() + ": " + provider.getCurrentTab().getLocalizedName(), 6, 0x404040);
		GlStateManager.disableLighting();
		GlStateManager.color(1, 1, 1, 1);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		xFactor = guiLeft;
		yFactor = guiTop;
		if (mouseButton == 0) {
			for (int i = elements.size() - 1; i >= 0; i--) {
				Element element = elements.get(i);
				if (MechConstruct.proxy.getGuiAssembler().isInRect(this, element.x, element.y, element.getWidth(provider), element.getHeight(provider), mouseX, mouseY)) {
					element.isPressing = true;
					element.onStartPress(provider, this, mouseX, mouseY);
					for (Element e : elements) {
						if (e != element) {
							e.isPressing = false;
						}
					}
					break;
				} else {
					element.isPressing = false;
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
			for (int i = elements.size() - 1; i >= 0; i--) {
				Element element = elements.get(i);
				if (MechConstruct.proxy.getGuiAssembler().isInRect(this, element.x, element.y, element.getWidth(provider), element.getHeight(provider), mouseX, mouseY)) {
					element.isDragging = true;
					element.onDrag(provider, this, mouseX, mouseY);
					for (Element e : elements) {
						if (e != element) {
							e.isDragging = false;
						}
					}
					break;
				} else {
					element.isDragging = false;
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
			for (int i = elements.size() - 1; i >= 0; i--) {
				Element element = elements.get(i);
				if (MechConstruct.proxy.getGuiAssembler().isInRect(this, element.x, element.y, element.getWidth(provider), element.getHeight(provider), mouseX, mouseY)) {
					element.isReleasing = true;
					element.onRelease(provider, this, mouseX, mouseY);
					for (Element e : elements) {
						if (e != element) {
							e.isReleasing = false;
						}
					}
					break;
				} else {
					element.isReleasing = false;
				}
			}
		}
	}
}
