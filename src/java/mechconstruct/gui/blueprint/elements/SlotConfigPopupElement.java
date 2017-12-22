package mechconstruct.gui.blueprint.elements;

import mechconstruct.block.BlockMachine;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.gui.MechGui;
import mechconstruct.gui.blueprint.Sprite;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
		IBlockAccess blockAccess = machine.getWorld();
		BlockPos pos = machine.getPos();
		IBlockState state = blockAccess.getBlockState(pos);
		IBlockState actualState = Blocks.DIRT.getDefaultState().getActualState(blockAccess, pos);
		BlockRendererDispatcher dispatcher = FMLClientHandler.instance().getClient().getBlockRendererDispatcher();
		IBakedModel model = dispatcher.getBlockModelShapes().getModelForState(state.withProperty(BlockMachine.FACING, EnumFacing.NORTH));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, 4, 23);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, 23, -12, -90F, 1F, 0F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, 23, 23, -90F, 0F, 1F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, 23, 42, 90F, 1F, 0F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, 26, 23, 180F, 0F, 1F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, 26, 42, 90F, 0F, 1F, 0F);
	}

	public void drawState(MechGui gui,
	                      IBlockAccess blockAccess,
	                      IBakedModel model,
	                      IBlockState actualState,
	                      BlockPos pos,
	                      BlockRendererDispatcher dispatcher,
	                      int x,
	                      int y,
	                      float rotAngle,
	                      float rotX,
	                      float rotY,
	                      float rotZ) {

		GlStateManager.pushMatrix();
		GlStateManager.enableDepth();
		GlStateManager.translate(8 + gui.xFactor + this.x + x, 8 + gui.yFactor + this.y + y, 512);
		GlStateManager.scale(16F, 16F, 16F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		GlStateManager.scale(-1, -1, -1);
		if (rotAngle != 0) {
			GlStateManager.rotate(rotAngle, rotX, rotY, rotZ);
		}
		dispatcher.getBlockModelRenderer().renderModelBrightness(model, actualState, 1F, false);
		GlStateManager.disableDepth();
		GlStateManager.popMatrix();

/*		GlStateManager.pushMatrix();
		GlStateManager.enableDepth();
		//		GlStateManager.translate(8 + gui.xFactor + this.x + x, 8 + gui.yFactor + this.y + y, 1000);
		GlStateManager.translate(gui.xFactor + this.x + x, gui.yFactor + this.y + y, 512);
		if (rotAngle != 0) {
			GlStateManager.rotate(rotAngle, rotX, rotY, rotZ);
		}
		GlStateManager.scale(16F, 16F, 16F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		GlStateManager.scale(-1, -1, -1);
		GlStateManager.disableDepth();
		GlStateManager.popMatrix();*/
	}

	public void drawState(MechGui gui, IBlockAccess blockAccess, IBakedModel model, IBlockState actualState, BlockPos pos, BlockRendererDispatcher dispatcher, int x, int y) {
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, x, y, 0, 0, 0, 0);
	}
}