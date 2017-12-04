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
    private LinkedList<EnumFacing> facingValues = null;
    public String name;
    public Class<? extends BlockEntityMachine> entityClass;
    public boolean noFacing;

    public BlockMachine(String name, boolean noFacing, Class<? extends BlockEntityMachine> entityClass) {
        super(Material.IRON);
        this.entityClass = entityClass;
        this.noFacing = noFacing;
        this.name = name;
        setRegistryName(MechConstruct.MOD_ID, name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
        ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, 0, "machines/basic").setInvVariant("active=false,facing=north").setFileName(name));
    }

    public BlockMachine(String name, Class<? extends BlockEntityMachine> entityClass) {
        this(name, false, entityClass);
    }

    @Override
    public String getUnlocalizedName() {
        return "machine." + MechConstruct.MOD_ID + "." + name;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, noFacing ? new IProperty[]{ACTIVE} : new IProperty[]{FACING, ACTIVE});
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
}
