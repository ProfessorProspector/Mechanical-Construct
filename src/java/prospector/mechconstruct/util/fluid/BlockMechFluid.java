package prospector.mechconstruct.util.fluid;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.util.shootingstar.ShootingStar;
import prospector.mechconstruct.util.shootingstar.model.ModelCompound;

public class BlockMechFluid extends BlockFluidClassic {
	public BlockMechFluid(Fluid fluid, Material material) {
		super(fluid, material);
		setRegistryName(MechConstruct.MOD_ID, "fluid." + fluidName);
		ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "fluid").setFileName("fluids").setVariant(fluid.getName()));
	}
}
