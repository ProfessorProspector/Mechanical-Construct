package mechconstruct.item;

import mechconstruct.MechConstruct;
import mechconstruct.util.shootingstar.ShootingStar;
import mechconstruct.util.shootingstar.model.ModelCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends Item {
    public ItemWrench() {
        ShootingStar.registerModel(new ModelCompound(MechConstruct.MOD_ID, this));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }
}
