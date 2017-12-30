package mechconstruct.networking;

import io.netty.buffer.ByteBuf;
import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.slotconfig.SidedConfigData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSlotSync implements IMessage {
	BlockPos pos;
	SidedConfigData configData;

	public PacketSlotSync(BlockPos pos, SidedConfigData configData) {
		this.pos = pos;
		this.configData = configData;
	}

	public PacketSlotSync() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		configData = new SidedConfigData(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeTag(buf, configData.serializeNBT());
	}

	public static class Handler implements IMessageHandler<PacketSlotSync, IMessage> {
		@Override
		public IMessage onMessage(PacketSlotSync message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketSlotSync message, MessageContext context) {
			World world = MechConstruct.proxy.getClientWorld();
			if (world.isBlockLoaded(message.pos)) {
				BlockEntityMachine machine = (BlockEntityMachine) world.getTileEntity(message.pos);
				machine.setSideConfigData(message.configData);
			}
		}
	}
}
