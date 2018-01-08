package prospector.mechconstruct.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandler extends ItemStackHandler implements INBTSerializable<NBTTagCompound> {
	public ItemHandler() {
	}

	public ItemHandler(int size) {
		super(size);
	}

	public ItemHandler(NonNullList<ItemStack> stacks) {
		super(stacks);
	}
}
