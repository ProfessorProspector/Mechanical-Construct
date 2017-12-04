package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.EnergyUtils;

public class MachineFurnace extends BlockEntityMachine {
    public MachineFurnace() {
        super(2, 1000, EnergyUtils.Bandwidth.BASIC, 2);
    }

    @Override
    public void machineTick() {

    }
}
