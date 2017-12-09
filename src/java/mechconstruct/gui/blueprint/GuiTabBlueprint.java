package mechconstruct.gui.blueprint;

import mechconstruct.MechConstruct;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import net.minecraft.util.text.translation.I18n;

public class GuiTabBlueprint extends GuiBlueprint {
	private final String unlocalizedName;
	private final Sprite sprite;
	private ButtonElement.Action additionalAction;

	public GuiTabBlueprint(IBlueprintProvider provider, String name, Sprite sprite) {
		super(provider);
		this.unlocalizedName = "tab." + MechConstruct.MOD_ID + "." + name;
		this.sprite = sprite;
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, Sprite sprite, ButtonElement.Action additionalAction) {
		this(provider, name, sprite);
		this.additionalAction = additionalAction;
	}

	public String getLocalizedName() {
		return I18n.translateToLocal(unlocalizedName);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public ButtonElement.Action getAdditionalAction() {
		return additionalAction;
	}
}
