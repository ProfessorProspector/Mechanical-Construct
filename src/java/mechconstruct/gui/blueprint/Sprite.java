package mechconstruct.gui.blueprint;

import mechconstruct.gui.GuiAssemblerServer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Sprite implements ISprite {
	public static final Sprite EMPTY = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 0, 0, 0);
	public static final Sprite SLOT_NORMAL = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 0, 18, 18);
	public static final Sprite CHARGE_SLOT_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 18, 0, 18, 18);
	public static final Sprite DISCHARGE_SLOT_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 36, 0, 18, 18);
	public static final Sprite ENERGY_BAR = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 18, 12, 40);
	public static final Sprite ENERGY_BAR_BACKGROUND = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 12, 18, 14, 42);
	public static final Sprite TOP_ENERGY_BAR = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 215, 167, 2);
	public static final Sprite TOP_ENERGY_BAR_BACKGROUND = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 217, 169, 3);
	public static final Sprite LEFT_TAB = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 86, 23, 26);
	public static final Sprite LEFT_TAB_SELECTED = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 60, 29, 26);
	public static final Sprite CONFIGURE_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 26, 18, 16, 16);
	public static final Sprite REDSTONE_DISABLED_ICON = new Sprite(new ItemStack(Items.GUNPOWDER));
	public static final Sprite REDSTONE_LOW_ICON = new Sprite(new ItemStack(Items.REDSTONE));
	public static final Sprite REDSTONE_HIGH_ICON = new Sprite(new ItemStack(Blocks.REDSTONE_TORCH));
	public static final Sprite UPGRADE_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 26, 34, 16, 16);
	public static final Sprite ENERGY_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 46, 19, 9, 13);
	public static final Sprite ENERGY_ICON_EMPTY = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 62, 19, 9, 13);
	public static final Sprite JEI_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 42, 34, 16, 16);
	public static final Sprite BUTTON_SLOT_NORMAL = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 54, 0, 18, 18);
	public static final Sprite FAKE_SLOT = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 72, 0, 18, 18);
	public static final Sprite BUTTON_HOVER_OVERLAY_SLOT_NORMAL = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 90, 0, 18, 18);
	public static final Sprite MODEL_SLOT = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 108, 0, 18, 18);
	public static final Sprite.Button EXIT_BUTTON = new Sprite.Button(new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 74, 44, 13, 13), new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 87, 44, 13, 13));
	public static final Sprite.CheckBox LIGHT_CHECK_BOX = new Sprite.CheckBox(new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 74, 18, 13, 13), new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 87, 18, 16, 13));
	public static final Sprite.CheckBox DARK_CHECK_BOX = new Sprite.CheckBox(new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 74, 31, 13, 13), new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 87, 31, 16, 13));

	public final ResourceLocation textureLocation;
	public final int x;
	public final int y;
	public final int width;
	public final int height;
	public int offsetX = 0;
	public int offsetY = 0;
	public ItemStack itemStack;

	public Sprite(ResourceLocation textureLocation, int x, int y, int width, int height) {
		this.textureLocation = textureLocation;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.itemStack = null;
	}

	public Sprite(ItemStack stack) {
		this.textureLocation = null;
		this.x = -1;
		this.y = -1;
		this.width = -1;
		this.height = -1;
		this.itemStack = stack;
	}

	public boolean hasStack() {
		return itemStack != null;
	}

	public boolean hasTextureInfo() {
		return x >= 0 && y >= 0 && width >= 0 && height >= 0;
	}

	public Sprite setOffsetX(int offsetX) {
		this.offsetX = offsetX;
		return this;
	}

	public Sprite setOffsetY(int offsetY) {
		this.offsetY = offsetY;
		return this;
	}

	@Override
	public Sprite getSprite(IBlueprintProvider provider) {
		return this;
	}

	public static class Button {
		private Sprite normal;
		private Sprite hovered;

		public Button(Sprite normal, Sprite hovered) {
			this.normal = normal;
			this.hovered = hovered;
		}

		public Sprite getNormal() {
			return normal;
		}

		public Sprite getHovered() {
			return hovered;
		}
	}

	public static class ToggleButton {
		private Sprite normal;
		private Sprite hovered;
		private Sprite pressed;

		public ToggleButton(Sprite normal, Sprite hovered, Sprite pressed) {
			this.normal = normal;
			this.hovered = hovered;
			this.pressed = pressed;
		}

		public Sprite getNormal() {
			return normal;
		}

		public Sprite getHovered() {
			return hovered;
		}

		public Sprite getPressed() {
			return pressed;
		}
	}

	public static class CheckBox {
		private Sprite normal;
		private Sprite ticked;

		public CheckBox(Sprite normal, Sprite ticked) {
			this.normal = normal;
			this.ticked = ticked;
		}

		public Sprite getNormal() {
			return normal;
		}

		public Sprite getTicked() {
			return ticked;
		}
	}
}
