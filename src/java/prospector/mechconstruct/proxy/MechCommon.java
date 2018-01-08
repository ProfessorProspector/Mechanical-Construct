package prospector.mechconstruct.proxy;

import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.gui.GuiAssemblerServer;
import prospector.mechconstruct.gui.MechGuiHandler;
import prospector.mechconstruct.gui.blueprint.elements.Element;
import prospector.mechconstruct.network.MechPacketManager;
import prospector.mechconstruct.registry.MechRegistry;
import prospector.mechconstruct.registry.recipes.basic.BasicFurnaceRecipes;
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
		MechPacketManager.registerMessages(MechConstruct.MOD_ID);
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(MechConstruct.instance, new MechGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent event) {
		BasicFurnaceRecipes.postInit();
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
