package mechconstruct.gui.blueprint;

import mechconstruct.MechConstruct;
import mechconstruct.gui.blueprint.elements.ElementBase;
import net.minecraft.util.text.translation.I18n;

public class GuiTabBlueprint extends GuiBlueprint {
	private final String unlocalizedName;
	private SpriteContainer spriteContainer = new SpriteContainer();
	private ElementBase.Action additionalAction;

	public GuiTabBlueprint(IBlueprintProvider provider, String name, SpriteContainer spriteContainer) {
		super(provider);
		this.unlocalizedName = "tab." + MechConstruct.MOD_ID + "." + name;
		this.spriteContainer = spriteContainer;
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, ElementBase.Action additionalAction, SpriteContainer spriteContainer) {
		this(provider, name, spriteContainer);
		this.additionalAction = additionalAction;
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, OffsetSprite offsetSprite) {
		this(provider, name, new SpriteContainer().addSprite(offsetSprite));
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, ElementBase.Action additionalAction, OffsetSprite offsetSprite) {
		this(provider, name, offsetSprite);
		this.additionalAction = additionalAction;
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, ISprite sprite) {
		this(provider, name, new OffsetSprite(sprite, 5, 5));
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, ElementBase.Action additionalAction, ISprite sprite) {
		this(provider, name, sprite);
		this.additionalAction = additionalAction;
	}

	public String getLocalizedName() {
		return I18n.translateToLocal(unlocalizedName);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public ElementBase.Action getAdditionalAction() {
		return additionalAction;
	}

	public SpriteContainer getSpriteContainer() {
		return spriteContainer;
	}
}
