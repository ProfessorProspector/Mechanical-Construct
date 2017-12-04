package mechconstruct.proxy;

import mechconstruct.registry.MechRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MechCommon {

    public void preInit(FMLPreInitializationEvent event) {
        MechRegistry.registerBlockEntities();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
