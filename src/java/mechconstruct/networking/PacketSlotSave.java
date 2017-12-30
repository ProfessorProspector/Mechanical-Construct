package mechconstruct.networking;

import io.netty.buffer.ByteBuf;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.slotconfig.SlotCompound;
import mechconstruct.util.slotconfig.SlotConfig;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSlotSave implements IMessage {
	BlockPos pos;
	EnumFacing facing;
	SlotCompound slot;

	public PacketSlotSave(BlockPos pos, EnumFacing facing, SlotCompound slot) {
		this.pos = pos;
		this.facing = facing;
		this.slot = slot;
	}

	public PacketSlotSave() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		facing = EnumFacing.values()[buf.readInt()];
		slot = new SlotCompound(buf.readInt(), SlotConfig.values()[buf.readInt()]);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(facing.ordinal());
		buf.writeInt(slot.getId());
		buf.writeInt(slot.getSlotConfig().ordinal());
	}

	public static class Handler implements IMessageHandler<PacketSlotSave, IMessage> {
		@Override
		public IMessage onMessage(PacketSlotSave message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketSlotSave message, MessageContext context) {
			BlockEntityMachine machine = (BlockEntityMachine) context.getServerHandler().player.world.getTileEntity(message.pos);
			machine.updateSideSlot(message.facing, message.slot);
			machine.markDirty();

			PacketSlotSync packetSlotSync = new PacketSlotSync(message.pos, machine.getSideConfigData());
			MechPacketHandler.networkWrapper.sendToAll(packetSlotSync);
		}
	}
}
