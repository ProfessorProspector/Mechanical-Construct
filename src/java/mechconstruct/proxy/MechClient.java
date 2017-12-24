package mechconstruct.proxy;

import mechconstruct.gui.GuiAssembler;
import mechconstruct.gui.GuiAssemblerServer;
import mechconstruct.gui.blueprint.elements.Element;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MechClient extends MechCommon {

	private GuiAssembler guiAssembler = new GuiAssembler();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	public GuiAssemblerServer getGuiAssembler() {
		return guiAssembler;
	}

	public void clientCalls(Element element) {
		element.clientCalls();
	}
}
