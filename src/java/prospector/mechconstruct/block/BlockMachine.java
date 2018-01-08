package prospector.mechconstruct.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.blockentities.BlockEntityMachine;
import prospector.mechconstruct.util.shootingstar.ShootingStar;
import prospector.mechconstruct.util.shootingstar.model.ModelCompound;

import javax.annotation.Nullable;

public class BlockMachine extends Block {
	protected String name;
	protected String tier;
	protected Class<? extends BlockEntityMachine> entityClass;

	public BlockMachine(String name, String tier, Class<? extends BlockEntityMachine> entityClass, boolean regModel) {
		super(Material.IRON);
		this.entityClass = entityClass;
		this.name = name;
		this.tier = tier;
		if (regModel) {
			if (!tier.isEmpty()) {
				setRegistryName(MechConstruct.MOD_ID, tier + "." + name);
				ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "block/machine/" + tier).setInvVariant("normal").setFileName(name));
			} else {
				setRegistryName(MechConstruct.MOD_ID, name);
				ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "block/machine").setInvVariant("normal").setFileName(name));
			}
		}
	}

	public BlockMachine(String name, String tier, Class<? extends BlockEntityMachine> entityClass) {
		this(name, tier, entityClass, true);
	}

	@Override
	public String getUnlocalizedName() {
		if (!tier.isEmpty()) {
			return "machine." + MechConstruct.MOD_ID + "." + tier + "." + name;
		} else {
			return "machine." + MechConstruct.MOD_ID + "." + name;
		}
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
		if (worldIn.getTileEntity(pos) instanceof BlockEntityMachine && !((BlockEntityMachine) worldIn.getTileEntity(pos)).getGuiTabBlueprints().isEmpty()) {
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
