package prospector.mechconstruct.network;

import io.netty.buffer.ByteBuf;
import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.blockentities.BlockEntityMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTankSync implements IMessage {
	public BlockPos pos;
	public NBTTagCompound tag;

	public PacketTankSync() {
	}

	public PacketTankSync(BlockPos pos, NBTTagCompound tag) {
		this.pos = pos;
		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler implements IMessageHandler<PacketTankSync, IMessage> {
		@Override
		public IMessage onMessage(PacketTankSync message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketTankSync message, MessageContext context) {
			World world = MechConstruct.proxy.getClientWorld();
			if (world.isBlockLoaded(message.pos)) {
				BlockEntityMachine machine = (BlockEntityMachine) world.getTileEntity(message.pos);
				if (machine != null && machine.getFluidInventory() != null) {
					machine.getFluidInventory().deserializeNBT(message.tag);
				}
			}
		}
	}
}
