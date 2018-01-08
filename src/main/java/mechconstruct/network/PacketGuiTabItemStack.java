package mechconstruct.network;

import io.netty.buffer.ByteBuf;
import mechconstruct.MechConstruct;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiTabItemStack implements IMessage {
	public int tab;

	public PacketGuiTabItemStack() {
	}

	public PacketGuiTabItemStack(int tab) {
		this.tab = tab;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		tab = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(tab);
	}

	public static class Handler implements IMessageHandler<PacketGuiTabItemStack, IMessage> {
		@Override
		public IMessage onMessage(PacketGuiTabItemStack message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketGuiTabItemStack message, MessageContext context) {
			EntityPlayerMP playerEntity = context.getServerHandler().player;
			playerEntity.inventory.getCurrentItem().getTagCompound().setInteger(MechConstruct.MOD_ID + ":selected_tab", message.tab);
		}
	}
}
