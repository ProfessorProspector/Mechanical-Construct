package prospector.mechconstruct.gui;

import prospector.mechconstruct.blockentities.BlockEntityMachine;
import prospector.mechconstruct.gui.blueprint.IBlueprintProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class MechGuiHandler implements IGuiHandler {
	@Nullable
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof IBlueprintProvider) {
			return ((IBlueprintProvider) tile).getContainer(((IBlueprintProvider) tile).getCurrentTab(), player);
		}
		return null;
	}

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile instanceof BlockEntityMachine) {
			return new MechGui(((BlockEntityMachine) tile).getCurrentTab(), player);
		}
		return null;
	}
}
