package prospector.mechconstruct.blockentities;

import prospector.mechconstruct.block.BlockExhaustPipe;
import prospector.mechconstruct.registry.MechFluids;
import prospector.mechconstruct.util.Tank;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;

public class ExhaustPipe extends BlockEntityMachine {
	public static final String FLUE = "flue";

	public ExhaustPipe() {
		super(new Tank(FLUE, 50).setFilter(fluid -> fluid.getFluid().isGaseous()));
		this.mainBlueprint = null;
	}

	@Override
	public void machineTick() {
		if (!world.isRemote && world.getBlockState(pos).getValue(BlockExhaustPipe.TOP)) {
			getFluidInventory().fillTank(FLUE, MechFluids.FLUE_GAS, 25);
			if (world.rand.nextInt(15) == 0) {
				int fluidAmount = getFluidInventory().getTank(FLUE).getFluidAmount();
				if (fluidAmount > 0) {
					getFluidInventory().drainTank(FLUE, fluidAmount);
					((WorldServer) getWorld()).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, (int) Math.round(15 * ((double) fluidAmount / 50)), 0, 0.2, 0, 0.005);
				}
			}
		}
	}
}
