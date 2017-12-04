package mechconstruct.gui;

import net.minecraft.util.ResourceLocation;

public class Sprite {
	public static final Sprite SLOT_NORMAL = new Sprite(GuiAssembler.MECH_ELEMENTS, 0, 0, 18, 18);
	public static final Sprite CHARGE_SLOT_ICON = new Sprite(GuiAssembler.MECH_ELEMENTS, 18, 0, 18, 18);
	public static final Sprite DISCHARGE_SLOT_ICON = new Sprite(GuiAssembler.MECH_ELEMENTS, 36, 0, 18, 18);
	public static final Sprite ENERGY_BAR = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 0, 18, 12, 40);
	public static final Sprite ENERGY_BAR_BACKGROUND = new Sprite(GuiAssemblerServer.MECH_ELEMENTS, 12, 18, 14, 42);

	public final ResourceLocation textureLocation;
	public final int x;
	public final int y;
	public final int width;
	public final int height;

	public Sprite(ResourceLocation textureLocation, int x, int y, int width, int height) {
		this.textureLocation = textureLocation;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
