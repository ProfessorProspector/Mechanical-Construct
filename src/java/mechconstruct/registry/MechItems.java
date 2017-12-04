package mechconstruct.registry;

import mechconstruct.MechConstruct;
import mechconstruct.item.ItemWrench;
import net.minecraft.item.Item;

import java.util.ArrayList;

public class MechItems {
	protected static final ArrayList<Item> ITEMS = new ArrayList<>();

	public static final Item WRENCH = register(new ItemWrench());

	public static Item register(Item item) {
		item.setCreativeTab(MechConstruct.TAB);
		ITEMS.add(item);
		return item;
	}

	public static ArrayList<Item> getItems() {
		return ITEMS;
	}
}
