package mechconstruct.block;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.shootingstar.ShootingStar;
import mechconstruct.util.shootingstar.model.ModelCompound;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.LinkedList;

public class BlockExhaustPipe extends BlockMachine {
	public static final PropertyBool TOP = PropertyBool.create("top");
	protected LinkedList<EnumFacing> facingValues = null;

	public BlockExhaustPipe(String name, Class<? extends BlockEntityMachine> entityClass) {
		super(name, "", entityClass, false);
		setDefaultState(blockState.getBaseState().withProperty(TOP, false));
		setRegistryName(MechConstruct.MOD_ID, name);
		ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "machines").setInvVariant("top=false").setFileName(name));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TOP);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return meta == 0 ? getDefaultState().withProperty(TOP, false) : getDefaultState().withProperty(TOP, true);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return !state.getValue(TOP) ? 0 : 1;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
