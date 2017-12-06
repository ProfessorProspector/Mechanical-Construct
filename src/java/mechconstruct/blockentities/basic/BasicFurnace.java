package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.SlotType;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.GuiBlueprint;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.gui.blueprint.elements.ButtonElement;
import mechconstruct.util.EnergyUtils;
import net.minecraft.item.ItemStack;

public class BasicFurnace extends BlockEntityMachine {
	public BasicFurnace() {
		super(2, 1000, EnergyUtils.Bandwidth.BASIC, 2);
	}

	@Override
	public void machineTick() {

	}

	@Override
	public GuiBlueprint getGuiBlueprint() {
		return new GuiBlueprint(this)
			.addSlot(SlotType.NORMAL, 20, 20)
			.addSlot(SlotType.NORMAL, 40, 20)
			.addButton(new ButtonElement(-26, 5, 29, 26,
				new SpriteContainer(Sprite.LEFT_TAB_SELECTED),
				new SpriteContainer(new Sprite(new ItemStack(this.block)), 5, 5))
				.addPressAction((button, machine, mouseX, mouseY) -> {
				}))
			.addButton(new ButtonElement(-23, 5 + (26 + 1), 23, 26,
				new SpriteContainer(Sprite.LEFT_TAB),
				new SpriteContainer(Sprite.CONFIGURE_ICON, 5, 5))
				.addPressAction((button, machine, mouseX, mouseY) -> {
				}));
	}
}
