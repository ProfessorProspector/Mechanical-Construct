package mechconstruct.gui.blueprint.elements;

import mechconstruct.MechConstruct;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotConfigPopupElement extends Element {
	int id;

	public SlotConfigPopupElement(int slotId, int x, int y) {
		super(x, y);
		this.id = slotId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void clientCalls() {
		super.clientCalls();
		int width = 90;
		int height = 101;
		setWidth(width);
		setHeight(height);
		setX(x + 31 - (width / 2));
		setY(y + 47 - (height / 2));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void draw(MechGui gui) {
		super.draw(gui);
		MechConstruct.proxy.getGuiAssembler().drawDefaultBackground(gui, x, y, getWidth(gui.provider), getHeight(gui.provider));
		int configX = x - 31 + (getWidth(gui.provider) / 2);
		int configY = y + 4;
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.MODEL_SLOT, configX + 3, configY + 22);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.MODEL_SLOT, configX + 22, configY + 3);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.MODEL_SLOT, configX + 22, configY + 22);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.MODEL_SLOT, configX + 22, configY + 41);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.MODEL_SLOT, configX + 41, configY + 22);
		MechConstruct.proxy.getGuiAssembler().drawSprite(gui, Sprite.MODEL_SLOT, configX + 41, configY + 41);
		BlockEntityMachine machine = ((BlockEntityMachine) gui.provider);
		IBlockAccess blockAccess = machine.getWorld();
		BlockPos pos = machine.getPos();
		IBlockState state = blockAccess.getBlockState(pos);
		IBlockState actualState = Blocks.DIRT.getDefaultState().getActualState(blockAccess, pos);
		BlockRendererDispatcher dispatcher = FMLClientHandler.instance().getClient().getBlockRendererDispatcher();
		IBakedModel model = dispatcher.getBlockModelShapes().getModelForState(state.withProperty(machine.getFacingProperty(), EnumFacing.NORTH));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, configX + 4, configY + 23);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, configX + 23, configY - 12, -90F, 1F, 0F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, configX + 23, configY + 23, -90F, 0F, 1F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, configX + 23, configY + 42, 90F, 1F, 0F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, configX + 26, configY + 23, 180F, 0F, 1F, 0F);
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, configX + 26, configY + 42, 90F, 0F, 1F, 0F);
	}

	@SideOnly(Side.CLIENT)
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
		GlStateManager.translate(8 + gui.xFactor + x, 8 + gui.yFactor + y, 512);
		GlStateManager.scale(16F, 16F, 16F);
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		GlStateManager.scale(-1, -1, -1);
		if (rotAngle != 0) {
			GlStateManager.rotate(rotAngle, rotX, rotY, rotZ);
		}
		dispatcher.getBlockModelRenderer().renderModelBrightness(model, actualState, 1F, false);
		GlStateManager.disableDepth();
		GlStateManager.popMatrix();
	}

	@SideOnly(Side.CLIENT)
	public void drawState(MechGui gui, IBlockAccess blockAccess, IBakedModel model, IBlockState actualState, BlockPos pos, BlockRendererDispatcher dispatcher, int x, int y) {
		drawState(gui, blockAccess, model, actualState, pos, dispatcher, x, y, 0, 0, 0, 0);
	}
}