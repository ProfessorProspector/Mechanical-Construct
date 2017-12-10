package mechconstruct.gui.blueprint.elements;

import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.IBlueprintProvider;
import mechconstruct.gui.blueprint.SpriteContainer;
import mechconstruct.proxy.MechClient;

import java.util.ArrayList;
import java.util.List;

public class ButtonElement extends ElementBase {
	public String text;
	public int color;
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

	public ButtonElement(String text, int color, int x, int y, int width, int height, SpriteContainer... sprites) {
		super(x, y, sprites);
		this.text = text;
		this.color = color;
		this.width = width;
		this.height = height;
	}

	public ButtonElement(int x, int y, int width, int height, SpriteContainer... sprites) {
		this("", 0, x, y, width, height, sprites);
	}

	@Override
	public void draw(MechGui gui) {
		super.draw(gui);
		if (!text.isEmpty()) {
			MechClient.GUI_ASSEMBLER.drawCenteredString(gui, text, x + (width / 2), y + (height / 2), color);
		}
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

	public void onHover(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (Action action : hoverActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
	}

	public void onDrag(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (Action action : dragActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
	}

	public void onStartPress(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (Action action : startPressActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
	}

	public void onRelease(IBlueprintProvider provider, MechGui gui, int mouseX, int mouseY) {
		for (Action action : releaseActions) {
			action.execute(this, gui, provider, mouseX, mouseY);
		}
		if (isPressing) {
			for (Action action : pressActions) {
				action.execute(this, gui, provider, mouseX, mouseY);
			}
		}
	}

	public interface Action {
		void execute(ButtonElement element, MechGui gui, IBlueprintProvider provider, int mouseX, int mouseY);
	}

}
