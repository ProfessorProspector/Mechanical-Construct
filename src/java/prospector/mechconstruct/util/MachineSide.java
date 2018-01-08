package prospector.mechconstruct.util;

import prospector.mechconstruct.blockentities.BlockEntityMachine;
import net.minecraft.util.EnumFacing;

public enum MachineSide {
	FRONT("front"),
	BACK("back"),
	TOP("top"),
	BOTTOM("bottom"),
	LEFT("left"),
	RIGHT("right");

	public String name;

	MachineSide(String name) {
		this.name = name;
	}

	public static MachineSide getMachineSide(BlockEntityMachine machine, EnumFacing facing) {
		if (machine.getFacing() != null) {
			if (facing == machine.getFacing()) {
				return FRONT;
			}
			if (facing == machine.getFacing().getOpposite()) {
				return BACK;
			}
			if (facing == machine.getFacing().rotateY()) {
				return LEFT;
			}
			if (facing == machine.getFacing().rotateY().rotateY().rotateY()) {
				return RIGHT;
			}
			if (facing == EnumFacing.UP) {
				return TOP;
			}
			if (facing == EnumFacing.DOWN) {
				return BOTTOM;
			}
		}
		return FRONT;
	}

	public EnumFacing getFacing(BlockEntityMachine machine) {
		if (machine.getFacing() != null) {
			if (this == FRONT) {
				return machine.getFacing();
			}
			if (this == BACK) {
				return machine.getFacing().getOpposite();
			}
			if (this == RIGHT) {
				//North -> West
				int i = machine.getFacing().getOpposite().getHorizontalIndex() + 1;
				if (i > 3) {
					i = 0;
				}
				if (i < 0) {
					i = 3;
				}
				return EnumFacing.HORIZONTALS[i];
			}
			if (this == LEFT) {
				//North -> East
				int i = machine.getFacing().getOpposite().getHorizontalIndex() - 1;
				if (i > 3) {
					i = 0;
				}
				if (i < 0) {
					i = 3;
				}
				return EnumFacing.HORIZONTALS[i];
			}
			if (this == TOP) {
				return EnumFacing.UP;
			}
			if (this == BOTTOM) {
				return EnumFacing.DOWN;
			}
		}
		return EnumFacing.NORTH;
	}

	public String getName() {
		return name;
	}
}
