package mechconstruct.util.fluid;

import mechconstruct.MechConstruct;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class MechFluid extends Fluid {
	public MechFluid(String fluidName, int density, int viscosity, int temperature, int luminosity) {
		super(fluidName, new ResourceLocation(MechConstruct.MOD_ID, "blocks/fluids/" + fluidName + "_still"), new ResourceLocation(MechConstruct.MOD_ID, "blocks/fluids/" + fluidName + "_flowing"));
		setDensity(density);
		setViscosity(viscosity);
		setTemperature(temperature);
		setGaseous(getDensity() < 0);
	}

	public MechFluid(String fluidName, int density, int viscosity, int temperature) {
		this(fluidName, density, viscosity, temperature, 0);
	}

	public MechFluid(String fluidName) {
		this(fluidName, 1000, 1000, 300);
	}

	@Override
	public String getUnlocalizedName() {
		return "fluid." + MechConstruct.MOD_ID + "." + fluidName;
	}
}
