package mechconstruct.block;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.shootingstar.ShootingStar;
import mechconstruct.util.shootingstar.model.ModelCompound;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;

public class BlockMachineFacingAndActive extends BlockMachine {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected LinkedList<EnumFacing> facingValues = null;

	public BlockMachineFacingAndActive(String name, String tier, boolean noFacing, Class<? extends BlockEntityMachine> entityClass) {
		super(name, tier, entityClass, false);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
		if (!tier.isEmpty()) {
			setRegistryName(MechConstruct.MOD_ID, tier + "." + name);
			ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "machines/" + tier).setInvVariant("active=false,facing=north").setFileName(name));
		} else {
			setRegistryName(MechConstruct.MOD_ID, name);
			ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "machines").setInvVariant("active=false,facing=north").setFileName(name));
		}
	}

	public BlockMachineFacingAndActive(String name, String tier, Class<? extends BlockEntityMachine> entityClass) {
		this(name, tier, false, entityClass);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ACTIVE);
	}

	public LinkedList<EnumFacing> getFacingValues() {
		if (facingValues == null) {
			facingValues = new LinkedList<>();
			facingValues.addAll(FACING.getAllowedValues());
		}
		return facingValues;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(ACTIVE, false);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int index = meta;
		if (index >= FACING.getAllowedValues().size()) {
			index -= FACING.getAllowedValues().size();
		}
		return getDefaultState().withProperty(FACING, getFacingValues().get(index));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = getFacingValues().indexOf(state.getValue(FACING));
		if (state.getValue(ACTIVE)) {
			meta += getFacingValues().size();
		}
		return meta;
	}
}
