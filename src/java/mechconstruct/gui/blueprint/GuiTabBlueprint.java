package mechconstruct.gui.blueprint;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.networking.MechPacketHandler;
import mechconstruct.networking.PacketGuiTabItemStack;
import mechconstruct.networking.PacketGuiTabMachine;
import net.minecraft.util.text.translation.I18n;

public class GuiTabBlueprint extends GuiBlueprint {
	public final ButtonElement.Action buttonAction;
	private final String unlocalizedName;
	private final Sprite sprite;
	public ButtonElement button = null;
	private ButtonElement.Action additionalAction;

	public GuiTabBlueprint(IBlueprintProvider provider, String name, Sprite sprite) {
		super(provider);
		this.unlocalizedName = "tab." + MechConstruct.MOD_ID + "." + name;
		this.sprite = sprite;
		this.buttonAction = (element, gui, blueprintProvider, mouseX, mouseY) -> {
			if (provider.getCurrentTab() != this) {
				button.x = -26;
				button.width = 29;
				button.setSprite(0, Sprite.LEFT_TAB_SELECTED);
				provider.getCurrentTab().button.x = -23;
				provider.getCurrentTab().button.width = 23;
				provider.getCurrentTab().button.setSprite(0, Sprite.LEFT_TAB);
				provider.setCurrentTab(this);
				if (gui.blueprint.provider.getProviderType() == IBlueprintProvider.ProviderType.ITEM) {
					MechPacketHandler.networkWrapper.sendToServer(new PacketGuiTabItemStack(provider.getGuiTabBlueprints().indexOf(this)));
				} else if (gui.blueprint.provider.getProviderType() == IBlueprintProvider.ProviderType.MACHINE) {
					MechPacketHandler.networkWrapper.sendToServer(new PacketGuiTabMachine(((BlockEntityMachine) provider).getPos(), gui.blueprint.provider.getGuiTabBlueprints().indexOf(this)));
				}
				additionalAction.execute(element, gui, blueprintProvider, mouseX, mouseY);
			}
		};
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
}
