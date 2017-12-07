package mechconstruct.gui;

import mechconstruct.MechConstruct;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import net.minecraft.util.text.translation.I18n;

public class GuiTab {
	private final String unlocalizedName;
	private final Sprite sprite;
	public ButtonElement button = null;
	private DynamicTabSprite dynSprite = null;
	private ButtonElement.Action buttonAction;

	public GuiTab(String name, Sprite sprite) {
		this.unlocalizedName = "tab." + MechConstruct.MOD_ID + "." + name;
		this.sprite = sprite;
		this.buttonAction = (element, gui, machine, mouseX, mouseY) -> {
			if (gui.currentTab != this) {
				button.x = -26;
				button.width = 29;
				button.setSprite(0, Sprite.LEFT_TAB_SELECTED);
				gui.currentTab.button.x = -23;
				gui.currentTab.button.width = 23;
				gui.currentTab.button.setSprite(0, Sprite.LEFT_TAB);
				gui.currentTab = this;
			}
		};
	}

	public GuiTab(String name, Sprite sprite, ButtonElement.Action buttonAction) {
		this(name, sprite);
		this.buttonAction = buttonAction;
	}

	public GuiTab(String name, DynamicTabSprite sprite) {
		this(name, sprite.getSprite());
	}

	public GuiTab(String name, DynamicTabSprite sprite, ButtonElement.Action buttonAction) {
		this(name, sprite.getSprite(), buttonAction);
	}

	public String getLocalizedName() {
		return I18n.translateToLocal(unlocalizedName);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public Sprite getSprite() {
		if (dynSprite != null) {
			return dynSprite.getSprite();
		}
		return sprite;
	}

	public ButtonElement.Action getButtonAction() {
		return buttonAction;
	}

	public interface DynamicTabSprite {
		Sprite getSprite();
	}
}
