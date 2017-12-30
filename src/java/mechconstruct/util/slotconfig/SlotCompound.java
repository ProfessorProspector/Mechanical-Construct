package mechconstruct.util.slotconfig;

public class SlotCompound {
	private final int id;
	private SlotConfig config;

	public SlotCompound(int id, SlotConfig config) {
		this.id = id;
		this.config = config;
	}

	public int getId() {
		return id;
	}

	public SlotConfig getSlotConfig() {
		return config;
	}

	public SlotCompound setSlotConfig(SlotConfig config) {
		this.config = config;
		return this;
	}
}
