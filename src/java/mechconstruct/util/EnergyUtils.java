package mechconstruct.util;

import net.minecraft.util.text.translation.I18n;

public class EnergyUtils {


    public enum Bandwidth {
        NONE("null", 0),
        BASIC("basic", 500),
        ADVANCED("advanced", 5000),
        INDUSTRIAL("industrial", 50000),
        UNTHROTTLED("unthrottled", Integer.MAX_VALUE);

        private String unlocalizedName;
        private int maxInput;
        private int maxOutput;

        Bandwidth(String unlocalizedName, int maxIO) {
            this(unlocalizedName, maxIO, maxIO);
        }

        Bandwidth(String unlocalizedName, int maxInput, int maxOutput) {
            this.unlocalizedName = unlocalizedName;
            this.maxInput = maxInput;
            this.maxOutput = maxOutput;
        }

        public String getName() {
            return I18n.translateToLocal(getUnlocalizedName());
        }

        public String getUnlocalizedName() {
            return "bandwidth." + unlocalizedName;
        }

        public int getMaxInput() {
            return maxInput;
        }

        public int getMaxOutput() {
            return maxOutput;
        }
    }
}
