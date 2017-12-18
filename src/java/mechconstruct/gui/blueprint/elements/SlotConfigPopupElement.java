package mechconstruct.gui.blueprint.elements;

import mechconstruct.block.BlockMachine;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.Sprite;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
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
		IBakedModel model = dispatcher.getBlockModelShapes().getModelForState(machine.getWorld().getBlockState(machine.getPos()).withProperty(BlockMachine.FACING, EnumFacing.NORTH));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + 4, 8 + gui.yFactor + this.y + 23, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
		drawState(model, dispatcher);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + 23, 8 + gui.yFactor + this.y + 4, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
		drawState(model, dispatcher);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + 23, 8 + gui.yFactor + this.y + 23, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		drawState(model, dispatcher);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + 23, 8 + gui.yFactor + this.y + 42, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
		drawState(model, dispatcher);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + 42, 8 + gui.yFactor + this.y + 23, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
		drawState(model, dispatcher);
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(8 + gui.xFactor + this.x + 42, 8 + gui.yFactor + this.y + 42, 100);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		drawState(model, dispatcher);
		GlStateManager.popMatrix();
	}

	public void drawState(IBakedModel model, BlockRendererDispatcher dispatcher) {
		GlStateManager.scale(16, 16, 16);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		dispatcher.getBlockModelRenderer().renderModelBrightnessColor(model, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}