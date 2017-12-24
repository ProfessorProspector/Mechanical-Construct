package mechconstruct.gui.blueprint.elements;

import mechconstruct.MechConstruct;
import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CheckBoxElement extends Element {
	public String label;
	public int labelColor;
	public boolean isTicked;
	private Sprite.CheckBox checkBoxSprite;

	public CheckBoxElement(String label, int labelColor, int x, int y, boolean isTicked, Sprite.CheckBox checkBoxSprite) {
		super(x, y, checkBoxSprite.getNormal());
		this.checkBoxSprite = checkBoxSprite;
		this.isTicked = isTicked;
		this.label = label;
		this.labelColor = labelColor;
		if (isTicked) {
			container.setSprite(0, checkBoxSprite.getTicked());
		} else {
			container.setSprite(0, checkBoxSprite.getNormal());
		}
	}

	public CheckBoxElement(int x, int y, boolean isTicked, Sprite.CheckBox checkBoxSprite) {
		this("", 0x0, x, y, isTicked, checkBoxSprite);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(MechGui gui) {
		super.draw(gui);
		MechConstruct.proxy.getGuiAssembler().drawString(gui, label, x + checkBoxSprite.getNormal().width + 5, ((y + getHeight(gui.provider) / 2) - (gui.mc.fontRenderer.FONT_HEIGHT / 2)), labelColor);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void clientCalls() {
		super.clientCalls();
		this.addPressAction((element, gui, provider, mouseX, mouseY) -> {
			this.isTicked = !this.isTicked;
			if (this.isTicked) {
				element.container.setSprite(0, checkBoxSprite.getTicked());
			} else {
				element.container.setSprite(0, checkBoxSprite.getNormal());
			}
		});
	}

	public boolean isTicked() {
		return isTicked;
	}

	public void setTicked(boolean ticked) {
		isTicked = ticked;
		if (isTicked) {
			container.setSprite(0, checkBoxSprite.getTicked());
		} else {
			container.setSprite(0, checkBoxSprite.getNormal());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getWidth(IBlueprintProvider provider) {
		return checkBoxSprite.getNormal().width + Minecraft.getMinecraft().fontRenderer.getStringWidth(label) + 5;
	}
}
