/*
 * Copyright (c) 2017 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package prospector.shootingstar;

import prospector.shootingstar.model.ModelCompound;
import prospector.shootingstar.model.ModelMethods;

import java.util.ArrayList;
import java.util.List;

import static prospector.shootingstar.model.ModelMethods.registerItemModel;

public class ShootingStar {
	protected static List<ModelCompound> modelList = new ArrayList<>();

	public static void registerModel(ModelCompound modelCompound) {
		modelList.add(modelCompound);
	}

	public static void registerModels(String modid) {
		for (ModelCompound compound : modelList) {
			if (compound.getModid().equals(modid)) {
				if (compound.isBlock()) {
					if (compound.getFileName().equals("shootingstar.undefinedfilename"))
						registerItemModel(compound.getItem(), compound.getMeta(), compound.getBlockStatePath(), compound.getInventoryVariant());
					else
						registerItemModel(compound.getItem(), compound.getMeta(), compound.getFileName(), compound.getBlockStatePath(), compound.getInventoryVariant());
				}
				if (compound.isBlock()) {
					if (compound.getFileName().equals("shootingstar.undefinedfilename"))
						ModelMethods.setBlockStateMapper(compound.getBlock(), compound.getBlockStatePath(), compound.getIgnoreProperties());
					else
						ModelMethods.setBlockStateMapper(compound.getBlock(), compound.getFileName(), compound.getBlockStatePath(), compound.getIgnoreProperties());
				}
			}
		}
	}
}
