package mechconstruct.gui.blueprint.elements;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SlotConfigPopupElement extends ElementBase {
	int id;

	public SlotConfigPopupElement(int slotId, int x, int y) {
		super(x, y, Sprite.SLOT_CONFIG_POPUP);
		this.id = slotId;
	}

	@Override
	public void draw(MechGui gui) {
		super.draw(gui);
		BlockEntityMachine machine = ((BlockEntityMachine) gui.provider);
		BlockRendererDispatcher dispatcher = FMLClientHandler.instance().getClient().getBlockRendererDispatcher();
		IBakedModel model = dispatcher.getBlockModelShapes().getModelForState(machine.getWorld().getBlockState(machine.getPos().up()));
		//		IBakedModel model = dispatcher.getBlockModelShapes().getModelForState(machine.getWorld().getBlockState(machine.getPos()).withProperty(BlockMachine.FACING, EnumFacing.NORTH));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		drawState(gui, model, dispatcher, 4, 23, -90F, 0F, 1F, 0F);
		drawState(gui, model, dispatcher, 23, 4, 90F, 1F, 0F, 0F);
		drawState(gui, model, dispatcher, 23, 23, 180F, 0F, 1F, 0F);
		drawState(gui, model, dispatcher, 23, 42, -90F, 1F, 0F, 0F);
		drawState(gui, model, dispatcher, 42, 23, 90F, 0F, 1F, 0F);
		drawState(gui, model, dispatcher, 42, 42);
	}

	public void drawState(MechGui gui, IBakedModel model, BlockRendererDispatcher dispatcher, int x, int y, float rotAngle, float rotX, float rotY, float rotZ) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + x, 8 + gui.yFactor + this.y + y, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		if (rotAngle != 0) {
			GlStateManager.rotate(rotAngle, rotX, rotY, rotZ);
		}
		GlStateManager.scale(16, 16, 16);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		dispatcher.getBlockModelRenderer().renderModelBrightnessColor(model, 1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public void drawState(MechGui gui, IBakedModel model, BlockRendererDispatcher dispatcher, int x, int y) {
		drawState(gui, model, dispatcher, x, y, 0, 0, 0, 0);
	}
}