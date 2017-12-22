package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.blueprint.Sprite;

public class ButtonElement extends ElementBase {
	private Sprite.Button buttonSprite;

	public ButtonElement(int x, int y, Sprite.Button buttonSprite) {
		super(x, y, buttonSprite.getNormal());
		this.buttonSprite = buttonSprite;
		this.addUpdateAction((gui, element) -> {
			if (isHovering) {
				element.container.setSprite(0, buttonSprite.getHovered());
			} else {
				element.container.setSprite(0, buttonSprite.getNormal());
			}
		});
	}
}
