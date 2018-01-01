package mechconstruct.registry;

import com.google.common.base.CaseFormat;
import mechconstruct.MechConstruct;
import mechconstruct.block.BlockExhaustPipe;
import mechconstruct.block.BlockMachine;
import mechconstruct.block.BlockMachineFacingAndActive;
import mechconstruct.blockentities.ExhaustPipe;
import mechconstruct.blockentities.basic.BasicBoiler;
import mechconstruct.blockentities.basic.BasicFurnace;
import mechconstruct.blockentities.basic.BasicGrinder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class MechBlocks {
	protected static final ArrayList<Block> BLOCKS = new ArrayList<>();
	protected static final HashMap<String, Class<? extends TileEntity>> BLOCK_ENTITIES = new HashMap<>();

	public static final Block BASIC_FURNACE = register(new BlockMachineFacingAndActive("furnace", "basic", BasicFurnace.class));
	public static final Block BASIC_GRINDER = register(new BlockMachineFacingAndActive("grinder", "basic", BasicGrinder.class));
	public static final Block BASIC_BOILER = register(new BlockMachineFacingAndActive("boiler", "basic", BasicBoiler.class));
	public static final Block EXHAUST_PIPE = register(new BlockExhaustPipe("exhaust_pipe", ExhaustPipe.class));

	public static Block register(BlockMachine block) {
		String machineName;
		if (!block.getTier().isEmpty()) {
			machineName = "Mech" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, block.getTier() + "_" + block.getName());
		} else {
			machineName = "Mech" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, block.getName());
		}
		block.setCreativeTab(MechConstruct.TAB);
		BLOCKS.add(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		item.setUnlocalizedName(block.getUnlocalizedName());
		item.setCreativeTab(MechConstruct.TAB);
		MechItems.ITEMS.add(item);
		BLOCK_ENTITIES.put(machineName, block.getEntityClass());
		return block;
	}

	public static Block register(Block block) {
		return register(block, true);
	}

	public static Block register(Block block, boolean itemBlock) {
		block.setCreativeTab(MechConstruct.TAB);
		BLOCKS.add(block);
		if (itemBlock) {
			ItemBlock item = new ItemBlock(block);
			item.setRegistryName(block.getRegistryName());
			item.setUnlocalizedName(block.getUnlocalizedName());
			item.setCreativeTab(MechConstruct.TAB);
			MechItems.ITEMS.add(item);
		}
		return block;
	}

	public static Block register(Block block, Class<? extends TileEntity> blockEntity, String entityName) {
		return register(block, true, blockEntity, entityName);
	}

	public static Block register(Block block, boolean itemBlock, Class<? extends TileEntity> blockEntity, String entityName) {
		block.setCreativeTab(MechConstruct.TAB);
		BLOCKS.add(block);
		if (itemBlock) {
			ItemBlock item = new ItemBlock(block);
			item.setRegistryName(block.getRegistryName());
			item.setUnlocalizedName(block.getUnlocalizedName());
			item.setCreativeTab(MechConstruct.TAB);
			MechItems.ITEMS.add(item);
		}
		BLOCK_ENTITIES.put(entityName, blockEntity);
		return block;
	}

	public static ArrayList<Block> getBlocks() {
		return BLOCKS;
	}
}
