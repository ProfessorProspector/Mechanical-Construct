package mechconstruct.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtils {

	public static boolean isItemEqual(final ItemStack a, final ItemStack b, final boolean matchDamage,
	                                  final boolean matchNBT) {
		if (a == ItemStack.EMPTY || b == ItemStack.EMPTY)
			return false;
		if (a.getItem() != b.getItem())
			return false;
		if (matchNBT && !ItemStack.areItemStackTagsEqual(a, b))
			return false;
		if (matchDamage && a.getHasSubtypes()) {
			if (isWildcard(a) || isWildcard(b))
				return true;
			if (a.getItemDamage() != b.getItemDamage())
				return false;
		}
		return true;
	}

	public static boolean isItemEqual(ItemStack a, ItemStack b, boolean matchDamage, boolean matchNBT,
	                                  boolean useOreDic) {
		if (isItemEqual(a, b, matchDamage, matchNBT)) {
			return true;
		}
		if (a.isEmpty() || b.isEmpty())
			return false;
		if (useOreDic) {
			for (int inta : OreDictionary.getOreIDs(a)) {
				for (int intb : OreDictionary.getOreIDs(b)) {
					if (inta == intb) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isInputEqual(Object input, ItemStack other, boolean matchDamage, boolean matchNBT,
	                                   boolean useOreDic) {
		if (input instanceof ItemStack) {
			return isItemEqual((ItemStack) input, other, matchDamage, matchNBT, useOreDic);
		} else if (input instanceof String) {
			NonNullList<ItemStack> ores = OreDictionary.getOres((String) input);
			for (ItemStack stack : ores) {
				if (isItemEqual(stack, other, matchDamage, matchNBT, false)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isWildcard(ItemStack stack) {
		return isWildcard(stack.getItemDamage());
	}

	public static boolean isWildcard(int damage) {
		return damage == -1 || damage == OreDictionary.WILDCARD_VALUE;
	}
}