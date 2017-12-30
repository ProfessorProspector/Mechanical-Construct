package mechconstruct.proxy;

import mechconstruct.MechConstruct;
import mechconstruct.gui.GuiAssemblerServer;
import mechconstruct.gui.MechGuiHandler;
import mechconstruct.gui.blueprint.elements.Element;
import mechconstruct.networking.MechPacketHandler;
import mechconstruct.registry.MechRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class MechCommon {

	private GuiAssemblerServer guiAssembler = new GuiAssemblerServer();

	public void preInit(FMLPreInitializationEvent event) {
		MechRegistry.registerBlockEntities();
		MechRegistry.registerRecipes();
		MechPacketHandler.registerMessages(MechConstruct.MOD_ID);
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(MechConstruct.instance, new MechGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent event) {
	}

	public GuiAssemblerServer getGuiAssembler() {
		return guiAssembler;
	}

	public void clientCalls(Element element) {

	}

	public World getClientWorld() {
		return null;
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}
}
