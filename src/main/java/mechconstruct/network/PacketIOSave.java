package mechconstruct.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIOSave implements IMessage {

	BlockPos pos;
	int slotId;
	boolean input, output;

	public PacketIOSave(BlockPos pos, int slotID, boolean input, boolean output) {
		this.pos = pos;
		this.slotId = slotID;
		this.input = input;
		this.output = output;
	}

	public PacketIOSave() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		slotId = buf.readInt();
		input = buf.readBoolean();
		output = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(slotId);
		buf.writeBoolean(input);
		buf.writeBoolean(output);
	}

	public static class Handler implements IMessageHandler<PacketIOSave, IMessage> {
		@Override
		public IMessage onMessage(PacketIOSave message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketIOSave message, MessageContext context) {
/*			BlockEntityMachine machine = (BlockEntityMachine) context.getServerHandler().player.world.getTileEntity(message.pos);
			

			//Syncs back to the client
			PacketSlotSync packetSlotSync = new PacketSlotSync(message.pos, machine.slotConfiguration);
			MechPacketHandler.networkWrapper.sendToAll(packetSlotSync);*/
		}
	}
}
