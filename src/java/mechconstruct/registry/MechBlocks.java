package mechconstruct.registry;

import mechconstruct.block.BlockMachine;
import mechconstruct.blockentities.basic.BasicFurnace;
import mechconstruct.blockentities.basic.BasicGrinder;
import net.minecraft.block.Block;

public class MechBlocks {
    public static final Block BASIC_FURNACE = new BlockMachine("basic_furnace", BasicFurnace.class);
    public static final Block BASIC_GRINDER = new BlockMachine("basic_grinder", BasicGrinder.class);
}
