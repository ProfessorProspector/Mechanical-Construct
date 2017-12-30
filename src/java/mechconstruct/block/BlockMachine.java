package mechconstruct.block;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.shootingstar.ShootingStar;
import mechconstruct.util.shootingstar.model.ModelCompound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.LinkedList;

public class BlockMachine extends Block {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	protected String name;
	protected String tier;
	protected Class<? extends BlockEntityMachine> entityClass;
	protected boolean noFacing;
	protected LinkedList<EnumFacing> facingValues = null;

	public BlockMachine(String name, String tier, boolean noFacing, Class<? extends BlockEntityMachine> entityClass) {
		super(Material.IRON);
		this.entityClass = entityClass;
		this.noFacing = noFacing;
		this.name = name;
		this.tier = tier;
		setRegistryName(MechConstruct.MOD_ID, tier + "." + name);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
		ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, 0, "machines/" + tier).setInvVariant("active=false,facing=north").setFileName(name));
	}

	public BlockMachine(String name, String tier, Class<? extends BlockEntityMachine> entityClass) {
		this(name, tier, false, entityClass);
	}

	@Override
	public String getUnlocalizedName() {
		return "provider." + MechConstruct.MOD_ID + "." + tier + "." + name;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, noFacing ? new IProperty[] { ACTIVE } : new IProperty[] { FACING, ACTIVE });
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

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		try {
			BlockEntityMachine blockEntity = entityClass.newInstance();
			blockEntity.setBlock(this);
			return blockEntity;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Class<? extends BlockEntityMachine> getEntityClass() {
		return entityClass;
	}

	public String getName() {
		return name;
	}

	public String getTier() {
		return tier;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.getTileEntity(pos) != null) {
			playerIn.openGui(MechConstruct.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		BlockEntityMachine machine = (BlockEntityMachine) worldIn.getTileEntity(pos);
		if (machine.hasItemInventory()) {
			for (int i = 0; i < machine.getItemInventory().getSlots(); ++i) {
				ItemStack itemstack = machine.getItemInventory().getStackInSlot(i);
				if (!itemstack.isEmpty()) {
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
				}
			}
		}
		if (machine.hasUpgradeInventory()) {
			for (int i = 0; i < machine.getUpgradeInventory().getSlots(); ++i) {
				ItemStack itemstack = machine.getUpgradeInventory().getStackInSlot(i);
				if (!itemstack.isEmpty()) {
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}
}
