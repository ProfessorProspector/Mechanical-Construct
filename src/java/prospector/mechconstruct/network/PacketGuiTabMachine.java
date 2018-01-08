package prospector.mechconstruct.network;

import io.netty.buffer.ByteBuf;
import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.blockentities.BlockEntityMachine;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiTabMachine implements IMessage {
	public BlockPos pos;
	public int tab;

	public PacketGuiTabMachine() {
	}

	public PacketGuiTabMachine(BlockPos pos, int tab) {
		this.pos = pos;
		this.tab = tab;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		tab = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(tab);
	}

	public static class Handler implements IMessageHandler<PacketGuiTabMachine, IMessage> {
		@Override
		public IMessage onMessage(PacketGuiTabMachine message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketGuiTabMachine message, MessageContext context) {
			EntityPlayerMP playerEntity = context.getServerHandler().player;
			World world = playerEntity.getEntityWorld();
			if (world.isBlockLoaded(message.pos)) {
				BlockEntityMachine machine = (BlockEntityMachine) world.getTileEntity(message.pos);
				machine.setCurrentTab(message.tab);
				playerEntity.openGui(MechConstruct.instance, 0, playerEntity.getEntityWorld(), message.pos.getX(), message.pos.getY(), message.pos.getZ());
			}
		}
	}
}
