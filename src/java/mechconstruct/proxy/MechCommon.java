package mechconstruct.proxy;

import mechconstruct.MechConstruct;
import mechconstruct.gui.MechGuiHandler;
import mechconstruct.registry.MechRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class MechCommon {

	public void preInit(FMLPreInitializationEvent event) {
		MechRegistry.registerBlockEntities();
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(MechConstruct.instance, new MechGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent event) {
	}
}
