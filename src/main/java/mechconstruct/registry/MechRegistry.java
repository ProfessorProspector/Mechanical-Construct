package mechconstruct.registry;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.fluid.BlockMechFluid;
import mechconstruct.registry.recipes.basic.BasicFurnaceRecipes;
import mechconstruct.util.shootingstar.ShootingStar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MechConstruct.MOD_ID)
public class MechRegistry {
	private static ArrayList<Item> items = new ArrayList<>();
	private static ArrayList<Block> blocks = new ArrayList<>();
	private static ArrayList<Class<? extends BlockEntityMachine>> blockEntities = new ArrayList<>();

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ShootingStar.registerModels(MechConstruct.MOD_ID);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : MechItems.ITEMS) {
			event.getRegistry().register(item);
		}
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Fluid fluid : MechFluids.FLUIDS.keySet()) {
			FluidRegistry.registerFluid(fluid);
			MechBlocks.register(new BlockMechFluid(fluid, MechFluids.FLUIDS.get(fluid)), false);
			FluidRegistry.addBucketForFluid(fluid);
		}
		for (Block block : MechBlocks.BLOCKS) {
			event.getRegistry().register(block);
		}
	}

	public static void registerBlockEntities() {
		for (String entityName : MechBlocks.BLOCK_ENTITIES.keySet()) {
			GameRegistry.registerTileEntity(MechBlocks.BLOCK_ENTITIES.get(entityName), entityName);
		}
	}

	public static void registerRecipes() {
		BasicFurnaceRecipes.register();
	}
}

