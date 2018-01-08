package mechconstruct.registry;

import mechconstruct.util.fluid.MechFluid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

public class MechFluids {
	protected static final Map<Fluid, Material> FLUIDS = new HashMap<>();

	public static final Fluid STEAM = register(new MechFluid("steam", -1000, 700, 373), new MaterialLiquid(MapColor.SILVER));
	public static final Fluid FLUE_GAS = register(new MechFluid("flue_gas", -2000, 3000, 360), new MaterialLiquid(MapColor.GRAY));

	public static Fluid register(Fluid fluid, Material material) {
		FLUIDS.put(fluid, material);
		return fluid;
	}
}

