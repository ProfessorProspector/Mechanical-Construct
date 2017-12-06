package mechconstruct.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Sprite {
	public static final Sprite SLOT_NORMAL = new Sprite(GuiAssembler.MECH_ELEMENTS, 0, 0, 18, 18);
	public static final Sprite CHARGE_SLOT_ICON = new Sprite(GuiAssembler.MECH_ELEMENTS, 18, 0, 18, 18);
	public static final Sprite DISCHARGE_SLOT_ICON = new Sprite(GuiAssembler.MECH_ELEMENTS, 36, 0, 18, 18);
	public static final Sprite ENERGY_BAR = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 18, 12, 40);
	public static final Sprite ENERGY_BAR_BACKGROUND = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 12, 18, 14, 42);
	public static final Sprite LEFT_TAB = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 23, 86, 23, 26);
	public static final Sprite LEFT_TAB_SELECTED = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 60, 29, 26);
	public static final Sprite CONFIGURE_ICON = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 26, 18, 16, 16);

	public final ResourceLocation textureLocation;
	public final int x;
	public final int y;
	public final int width;
	public final int height;
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
}
