package mechconstruct;

import mechconstruct.proxy.MechCommon;
import mechconstruct.registry.MechItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

@Mod(name = MechConstruct.MOD_NAME, modid = MechConstruct.MOD_ID, version = MechConstruct.MOD_VERSION)
public class MechConstruct {

	public static final String MOD_NAME = "Mechanical Construct";
	public static final String MOD_ID = "mechconstruct";
	public static final String MOD_VERSION = "0.0.1DEV";
	public static final boolean ENABLE_PROPPAGE = true;
	public static final Random rand = new Random();
	public static final CreativeTabs TAB = new CreativeTabs(MechConstruct.MOD_ID) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(MechItems.getItems().get(rand.nextInt(MechItems.getItems().size())));
		}
	};
	@SidedProxy(clientSide = "mechconstruct.proxy.MechClient", serverSide = "mechconstruct.proxy.MechCommon")
	public static MechCommon proxy;

	@Mod.Instance
	public static MechConstruct instance;

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
