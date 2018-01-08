package prospector.mechconstruct.util.slotconfig;

import prospector.mechconstruct.gui.blueprint.GuiBlueprint;
import prospector.mechconstruct.util.MachineSide;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class SidedConfigData implements INBTSerializable<NBTTagCompound> {
	protected EnumMap<MachineSide, List<SlotCompound>> configMap = new EnumMap<>(MachineSide.class);

	public SidedConfigData(List<GuiBlueprint.SlotMapCompound> defaultSides) {
		for (MachineSide side : MachineSide.values()) {
			configMap.put(side, new ArrayList<>());
			for (GuiBlueprint.SlotMapCompound slotMapCompound : defaultSides) {
				updateSideSlot(side, slotMapCompound.getId(), slotMapCompound.getMap().getConfig(side));
			}
		}
	}

	public SidedConfigData(NBTTagCompound tagCompound) {
		deserializeNBT(tagCompound);
	}

	public void updateSideSlot(MachineSide side, SlotCompound compound) {
		List<SlotCompound> slots = configMap.get(side);
		slots.removeIf(c -> c.getId() == compound.getId());
		slots.add(compound);
		sort(side);
	}

	public void updateSideSlot(MachineSide side, int id, SlotConfig config) {
		updateSideSlot(side, new SlotCompound(id, config));
	}

	public List<SlotCompound> getSlotsForSide(MachineSide side) {
		sort(side);
		return configMap.get(side);
	}

	public void sort(MachineSide side) {
		configMap.get(side).sort((o1, o2) -> {
			if (o1.getId() < o2.getId()) {
				return -1;
			}
			if (o1.getId() > o2.getId()) {
				return 1;
			}
			return 0;
		});
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		Arrays.stream(MachineSide.values()).forEach(side -> {
			List<SlotCompound> slots = configMap.get(side);
			tagCompound.setInteger(side.getName() + "_size", slots.size());
			for (int i = 0; i < slots.size(); i++) {
				tagCompound.setInteger(side.getName() + "_id_" + i, slots.get(i).getId());
				tagCompound.setInteger(side.getName() + "_value_" + i, slots.get(i).getSlotConfig().ordinal());
			}
		});
		return tagCompound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound tagCompound) {
		Arrays.stream(MachineSide.values()).forEach(side -> {
			List<SlotCompound> slots = new ArrayList<>();
			for (int i = 0; i < tagCompound.getInteger(side.getName() + "_size"); i++) {
				int id = tagCompound.getInteger(side.getName() + "_id_" + i);
				SlotConfig config = SlotConfig.values()[tagCompound.getInteger(side.getName() + "_value_" + i)];
				slots.add(new SlotCompound(id, config));
			}
			configMap.put(side, slots);
		});
	}
}
