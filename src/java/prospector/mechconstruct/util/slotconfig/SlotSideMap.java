package prospector.mechconstruct.util.slotconfig;

import prospector.mechconstruct.util.MachineSide;

import java.util.EnumMap;

public class SlotSideMap {
	protected final EnumMap<MachineSide, SlotConfig> configMap = new EnumMap<>(MachineSide.class);

	public SlotSideMap(SlotConfig left,
	                   SlotConfig front,
	                   SlotConfig right,
	                   SlotConfig back,
	                   SlotConfig top,
	                   SlotConfig bottom) {
		this.configMap.put(MachineSide.LEFT, left);
		this.configMap.put(MachineSide.FRONT, front);
		this.configMap.put(MachineSide.RIGHT, right);
		this.configMap.put(MachineSide.BACK, back);
		this.configMap.put(MachineSide.TOP, top);
		this.configMap.put(MachineSide.BOTTOM, bottom);
	}

	public SlotConfig getConfig(MachineSide facing) {
		return configMap.get(facing);
	}

	public void setConfig(MachineSide facing, SlotConfig config) {
		this.configMap.put(facing, config);
	}

	public EnumMap<MachineSide, SlotConfig> getConfigMap() {
		return configMap;
	}

}
