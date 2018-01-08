package prospector.mechconstruct.util.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialGas extends Material {
	public MaterialGas(MapColor color) {
		super(color);
		this.setReplaceable();
		this.setNoPushMobility();
	}

	/**
	 * Returns if block of these materials are liquids.
	 */
	public boolean isLiquid() {
		return true;
	}

	/**
	 * Returns if this material is considered solid or not
	 */
	public boolean blocksMovement() {
		return false;
	}

	/**
	 * Returns true if the block is a considered solid. This is true by default.
	 */
	public boolean isSolid() {
		return false;
	}
}
