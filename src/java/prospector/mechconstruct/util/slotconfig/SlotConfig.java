package prospector.mechconstruct.util.slotconfig;

public enum SlotConfig {
	NONE(false, false),
	INPUT(false, true),
	OUTPUT(true, false);

	boolean extact;
	boolean insert;

	SlotConfig(boolean extact, boolean insert) {
		this.extact = extact;
		this.insert = insert;
	}

	public boolean isExtact() {
		return extact;
	}

	public boolean isInsert() {
		return insert;
	}

	public SlotConfig getNext() {
		int i = this.ordinal() + 1;
		if (i >= SlotConfig.values().length) {
			i = 0;
		}
		return SlotConfig.values()[i];
	}
}
