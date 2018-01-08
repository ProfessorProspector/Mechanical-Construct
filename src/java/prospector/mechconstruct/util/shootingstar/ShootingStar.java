package prospector.mechconstruct.util.shootingstar;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import prospector.mechconstruct.util.shootingstar.model.ModelCompound;
import prospector.mechconstruct.util.shootingstar.model.ModelMethods;

import java.util.ArrayList;
import java.util.List;

public class ShootingStar {
	protected static List<ModelCompound> modelList = new ArrayList<>();

	public static void registerModel(ModelCompound modelCompound) {
		modelList.add(modelCompound);
	}

	public static void registerModels(String modid) {
		for (ModelCompound compound : modelList) {
			if (compound.getModid().equals(modid)) {
				if (compound.isBlock()) {
					if (compound.getFileName().equals("shootingstar.undefinedfilename")) {
						//Checks if block has an item
						if (Item.getItemFromBlock(compound.getBlock()) != Items.AIR) {
							ModelMethods.registerItemModel(compound.getItem(), compound.getMeta(), compound.getBlockStatePath(), compound.getInventoryVariant());
						}
						if (shouldDoCustomVariant(compound)) {
							ModelMethods.setBlockStateMapper(compound.getBlock(), compound.getBlockStatePath(), compound.getVariant());
						} else {
							ModelMethods.setBlockStateMapper(compound.getBlock(), compound.getBlockStatePath(), compound.getIgnoreProperties());
						}
					} else {
						//Checks if block has an item
						if (Item.getItemFromBlock(compound.getBlock()) != Items.AIR) {
							ModelMethods.registerItemModel(compound.getItem(), compound.getMeta(), compound.getFileName(), compound.getBlockStatePath(), compound.getInventoryVariant());
						}
						if (shouldDoCustomVariant(compound)) {
							ModelMethods.setBlockStateMapper(compound.getBlock(), compound.getFileName(), compound.getBlockStatePath(), compound.getVariant());
						} else {
							ModelMethods.setBlockStateMapper(compound.getBlock(), compound.getFileName(), compound.getBlockStatePath(), compound.getIgnoreProperties());
						}
					}
				}
				if (compound.isItem()) {
					if (compound.getFileName().equals("shootingstar.undefinedfilename")) {
						ModelMethods.registerItemModel(compound.getItem(), compound.getMeta(), compound.getBlockStatePath(), compound.getInventoryVariant());
					} else {
						ModelMethods.registerItemModel(compound.getItem(), compound.getMeta(), compound.getFileName(), compound.getBlockStatePath(), compound.getInventoryVariant());
					}
				}
			}
		}

	}

	private static boolean shouldDoCustomVariant(ModelCompound compound) {
		if (!compound.getVariant().equals("shootingstar.undefinedvariant")) {
			return true;
		} else {
			return false;
		}
	}
}
