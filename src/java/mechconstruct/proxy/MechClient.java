package mechconstruct.proxy;

import mechconstruct.MechConstruct;
import mechconstruct.gui.GuiAssembler;
import mechconstruct.networking.MechPacketHandler;
import mechconstruct.util.shootingstar.ShootingStar;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MechConstruct.MOD_ID)
public class MechClient extends MechCommon {
	public static final GuiAssembler GUI_ASSEMBLER = new GuiAssembler();

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ShootingStar.registerModels(MechConstruct.MOD_ID);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MechPacketHandler.registerMessages(MechConstruct.MOD_ID);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
