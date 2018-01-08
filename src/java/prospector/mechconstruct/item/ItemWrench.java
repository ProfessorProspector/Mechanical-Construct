package prospector.mechconstruct.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import prospector.mechconstruct.mod.MechConstruct;
import prospector.mechconstruct.util.shootingstar.ShootingStar;
import prospector.mechconstruct.util.shootingstar.model.ModelCompound;

public class ItemWrench extends Item {
	public ItemWrench() {
		String name = "wrench";
		this.setRegistryName(MechConstruct.MOD_ID, name);
		this.setUnlocalizedName(MechConstruct.MOD_ID + "." + name);
		ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this, "item/tool"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.PASS;
	}
}
