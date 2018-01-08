package prospector.mechconstruct.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import prospector.mechconstruct.proxy.MechCommon;
import prospector.mechconstruct.registry.MechFluids;
import prospector.mechconstruct.registry.MechItems;

import java.util.ArrayList;
import java.util.Random;

@Mod(name = MechConstruct.MOD_NAME, modid = MechConstruct.MOD_ID, version = MechConstruct.MOD_VERSION)
public class MechConstruct {

	public static final String MOD_NAME = "Mechanical Construct";
	public static final String MOD_ID = "mechconstruct";
	public static final String MOD_VERSION = "0.0.1DEV";
	public static final boolean ENABLE_PROPPAGE = true;
	public static final Random rand = new Random();
	public static final CreativeTabs TAB = new CreativeTabs(MechConstruct.MOD_ID) {
		private ArrayList<ItemStack> buckets = new ArrayList<>();

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(MechItems.getItems().get(rand.nextInt(MechItems.getItems().size())));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			super.displayAllRelevantItems(list);
			if (buckets.isEmpty() && !MechFluids.getFluids().keySet().isEmpty()) {
				for (Fluid fluid : MechFluids.getFluids().keySet()) {
					buckets.add(FluidUtil.getFilledBucket(new FluidStack(fluid, 1000)).copy());
				}
			}
			list.addAll(buckets);
		}
	};
	@SidedProxy(clientSide = "prospector.mechconstruct.proxy.MechClient", serverSide = "prospector.mechconstruct.proxy.MechCommon")
	public static MechCommon proxy;

	@Mod.Instance
	public static MechConstruct instance;

	public MechConstruct() {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//USE PROXY
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		//USE PROXY
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//USE PROXY
		proxy.postInit(event);
	}
}
