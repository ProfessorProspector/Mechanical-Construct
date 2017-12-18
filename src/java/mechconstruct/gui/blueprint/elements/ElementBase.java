package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.ISprite;
import mechconstruct.gui.blueprint.OffsetSprite;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.proxy.MechClient;

import java.util.ArrayList;
import java.util.List;

public class ElementBase {

	public int x;
	public int y;
	public boolean isHovering = false;
	public boolean isDragging = false;
	public boolean isPressing = false;
	public boolean isReleasing = false;
	public boolean startPressLast = false;
	public boolean isHoveringLast = false;
	public boolean isDraggingLast = false;
	public boolean isPressingLast = false;
	public boolean isReleasingLast = false;
	public List<ElementBase.Action> hoverActions = new ArrayList<>();
	public List<ElementBase.Action> dragActions = new ArrayList<>();
	public List<ElementBase.Action> startPressActions = new ArrayList<>();
	public List<ElementBase.Action> pressActions = new ArrayList<>();
	public List<ElementBase.Action> releaseActions = new ArrayList<>();
	public SpriteContainer container;
	public List<UpdateAction> updateActions = new ArrayList<>();
	public List<UpdateAction> buttonUpdate = new ArrayList<>();
	private int width;
	private int height;

	public ElementBase(int x, int y, SpriteContainer container) {
		this.container = container;
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y, ISprite... sprites) {
		this.container = new SpriteContainer();
		for (ISprite sprite : sprites) {
			container.addSprite(sprite);
		}
		this.x = x;
		this.y = y;
	}

	public ElementBase(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public ElementBase(int x, int y, int width, int height, SpriteContainer container) {
		this.container = container;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public ElementBase(int x, int y, int width, int height, ISprite... sprites) {
		this.container = new SpriteContainer();
		for (ISprite sprite : sprites) {
			container.addSprite(sprite);
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public SpriteContainer getSpriteContainer() {
		return container;
	}

	public void adjustDimensions(IBlueprintProvider provider) {
		if (container.offsetSprites != null) {
			for (OffsetSprite offsetSprite : container.offsetSprites) {
				if (offsetSprite.getSprite().getSprite(provider).width + offsetSprite.getOffsetX(provider) > this.width) {
					this.width = offsetSprite.getSprite().getSprite(provider).width + offsetSprite.getOffsetX(provider);
				}
				if (offsetSprite.getSprite().getSprite(provider).height + offsetSprite.getOffsetY(provider) > this.height) {
					this.height = offsetSprite.getSprite().getSprite(provider).height + offsetSprite.getOffsetY(provider);
				}
			}
		}
	}

	public void draw(MechGui gui) {
		for (OffsetSprite sprite : getSpriteContainer().offsetSprites) {
			MechClient.GUI_ASSEMBLER.drawSprite(gui, sprite.getSprite(), x + sprite.getOffsetX(gui.provider), y + sprite.getOffsetY(gui.provider));
		}
	}

	public void renderUpdate(MechGui gui) {
		isHoveringLast = isHovering;
		isPressingLast = isPressing;
		isDraggingLast = isDragging;
		isReleasingLast = isReleasing;
	}

	public void update(MechGui gui) {
		for (UpdateAction action : updateActions) {
			action.update(gui, this);
		}
	}

	public ElementBase addUpdateAction(UpdateAction action) {
		updateActions.add(action);
		return this;
	}

	public ElementBase setWidth(int width) {
		this.width = width;
		return this;
	}

	public ElementBase setHeight(int height) {
		this.height = height;
		return this;
	}

	public int getX() {
		return x;
	}

	public ElementBase setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public ElementBase setY(int y) {
		this.y = y;
		return this;
	}

	public int getWidth(IBlueprintProvider provider) {
		adjustDimensions(provider);
		return width;
	}

	public int getHeight(IBlueprintProvider provider) {
		adjustDimensions(provider);
		return height;
	}

	public ElementBase addHoverAction(ElementBase.Action action) {
		this.hoverActions.add(action);
		return this;
	}

	public ElementBase addDragAction(ElementBase.Action action) {
		this.dragActions.add(action);
		return this;
	}

	public ElementBase addStartPressAction(ElementBase.Action action) {
		this.startPressActions.add(action);
		return this;
	}

	public ElementBase addPressAction(ElementBase.Action action) {
		this.pressActions.add(action);
		return this;
	}

	public ElementBase addReleaseAction(ElementBase.Action action) {
		this.releaseActions.add(action);
		return this;
	}

	public void onHover(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (ElementBase.Action action : hoverActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
	}

	public void onDrag(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (ElementBase.Action action : dragActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
	}

	public void onStartPress(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (ElementBase.Action action : startPressActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
	}

	public void onRelease(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (ElementBase.Action action : releaseActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
		if (isPressing) {
			for (ElementBase.Action action : pressActions) {
				action.execute(this, gui, provider, mouseX, mouseY);
			}
		}
	}

	public interface Action {
		void execute(ElementBase element, MechGui gui, IBlueprintProvider provider, int mouseX, int mouseY);
	}

	public interface UpdateAction {
		void update(MechGui gui, ElementBase element);
	}
}
