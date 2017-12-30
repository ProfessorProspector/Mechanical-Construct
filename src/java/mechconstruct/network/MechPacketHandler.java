package mechconstruct.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MechPacketHandler {
	public static int id = 0;
	public static SimpleNetworkWrapper networkWrapper = null;

	public MechPacketHandler() {
	}

	public static int nextId() {
		return id++;
	}

	public static void registerMessages(String channelName) {
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}

	public static void registerMessages() {
		networkWrapper.registerMessage(PacketGuiTabMachine.Handler.class, PacketGuiTabMachine.class, nextId(), Side.SERVER);
		networkWrapper.registerMessage(PacketGuiTabItemStack.Handler.class, PacketGuiTabItemStack.class, nextId(), Side.SERVER);
		networkWrapper.registerMessage(PacketIOSave.Handler.class, PacketIOSave.class, nextId(), Side.SERVER);
		networkWrapper.registerMessage(PacketSlotSave.Handler.class, PacketSlotSave.class, nextId(), Side.SERVER);
		networkWrapper.registerMessage(PacketSlotSync.Handler.class, PacketSlotSync.class, nextId(), Side.CLIENT);
		networkWrapper.registerMessage(PacketTankSync.Handler.class, PacketTankSync.class, nextId(), Side.CLIENT);
	}
}
