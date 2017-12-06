package mechconstruct.gui.blueprint.elements;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.GuiMachine;
import mechconstruct.gui.Sprite;
import mechconstruct.gui.blueprint.SpriteContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonElement extends ElementBase {
	public String text;
	public int width;
	public int height;
	public boolean isHovering = false;
	public boolean isDragging = false;
	public boolean isPressing = false;
	public boolean isReleasing = false;
	public boolean startPressLast = false;
	public boolean isHoveringLast = false;
	public boolean isDraggingLast = false;
	public boolean isPressingLast = false;
	public boolean isReleasingLast = false;
	public List<Action> hoverActions = new ArrayList<>();
	public List<Action> dragActions = new ArrayList<>();
	public List<Action> startPressActions = new ArrayList<>();
	public List<Action> pressActions = new ArrayList<>();
	public List<Action> releaseActions = new ArrayList<>();
	public List<SpriteContainer> spriteContainers;

	public ButtonElement(String text, int x, int y, int width, int height, SpriteContainer... sprites) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.spriteContainers = Arrays.asList(sprites);
	}

	public ButtonElement(int x, int y, int width, int height, SpriteContainer... sprites) {
		this("", x, y, width, height, sprites);
	}

	@Override
	public void draw(GuiMachine gui) {
	}

	public void update() {
		isHoveringLast = isHovering;
		isPressingLast = isPressing;
		isDraggingLast = isDragging;
		isReleasingLast = isReleasing;
	}

	public ButtonElement addHoverAction(Action action) {
		this.hoverActions.add(action);
		return this;
	}

	public ButtonElement addDragAction(Action action) {
		this.dragActions.add(action);
		return this;
	}

	public ButtonElement addStartPressAction(Action action) {
		this.startPressActions.add(action);
		return this;
	}

	public ButtonElement addPressAction(Action action) {
		this.pressActions.add(action);
		return this;
	}

	public ButtonElement addReleaseAction(Action action) {
		this.releaseActions.add(action);
		return this;
	}

	public void onHover(BlockEntityMachine machine, int mouseX, int mouseY) {
		for (Action action : hoverActions) {
			action.execute(this, machine, mouseX, mouseY);
		}
	}

	public void onDrag(BlockEntityMachine machine, int mouseX, int mouseY) {
		for (Action action : dragActions) {
			action.execute(this, machine, mouseX, mouseY);
		}
	}

	public void onStartPress(BlockEntityMachine machine, int mouseX, int mouseY) {
		for (Action action : startPressActions) {
			action.execute(this, machine, mouseX, mouseY);
		}
	}

	public void onRelease(BlockEntityMachine machine, int mouseX, int mouseY) {
		for (Action action : releaseActions) {
			action.execute(this, machine, mouseX, mouseY);
		}
		if (isPressing) {
			for (Action action : pressActions) {
				action.execute(this, machine, mouseX, mouseY);
			}
		}
	}

	public void setSprite(int index, Sprite sprite) {
		SpriteContainer container = spriteContainers.get(index);
		container.sprite = sprite;
		spriteContainers.set(index, container);
	}

	public interface Action {
		void execute(ButtonElement element, BlockEntityMachine machine, int mouseX, int mouseY);
	}

}
