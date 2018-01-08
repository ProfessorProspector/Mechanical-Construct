package prospector.mechconstruct.gui.blueprint;

import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.gui.blueprint.elements.Element;
import net.minecraft.util.text.translation.I18n;

import java.util.ArrayList;
import java.util.List;

public class GuiTabBlueprint extends GuiBlueprint {
	private final String unlocalizedName;
	public List<String> tooltipText = new ArrayList<>();
	private SpriteContainer spriteContainer = new SpriteContainer();
	private Element.Action additionalAction;

	public GuiTabBlueprint(IBlueprintProvider provider, String name, SpriteContainer spriteContainer) {
		super(provider);
		this.unlocalizedName = "gui." + MechConstruct.MOD_ID + "." + name;
		this.spriteContainer = spriteContainer;
		tooltipText.add(getLocalizedName());
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, Element.Action additionalAction, SpriteContainer spriteContainer) {
		this(provider, name, spriteContainer);
		this.additionalAction = additionalAction;
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, OffsetSprite offsetSprite) {
		this(provider, name, new SpriteContainer().addSprite(offsetSprite));
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, Element.Action additionalAction, OffsetSprite offsetSprite) {
		this(provider, name, offsetSprite);
		this.additionalAction = additionalAction;
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, ISprite sprite) {
		this(provider, name, new OffsetSprite(sprite, 5, 5));
	}

	public GuiTabBlueprint(IBlueprintProvider provider, String name, Element.Action additionalAction, ISprite sprite) {
		this(provider, name, sprite);
		this.additionalAction = additionalAction;
	}

	public String getLocalizedName() {
		return I18n.translateToLocal(unlocalizedName);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public Element.Action getAdditionalAction() {
		return additionalAction;
	}

	public SpriteContainer getSpriteContainer() {
		return spriteContainer;
	}
}
