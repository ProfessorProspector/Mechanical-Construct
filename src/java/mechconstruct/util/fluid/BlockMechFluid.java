package mechconstruct.util.fluid;

import mechconstruct.MechConstruct;
import mechconstruct.util.shootingstar.ShootingStar;
import mechconstruct.util.shootingstar.model.ModelCompound;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockMechFluid extends BlockFluidClassic {
	public BlockMechFluid(Fluid fluid, Material material) {
		super(fluid, material);
		setRegistryName(MechConstruct.MOD_ID, "fluid." + fluidName);
		ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this).setFileName("fluids").setVariant(fluid.getName()));
	}
}
