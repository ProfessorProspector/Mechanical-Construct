package mechconstruct.blockentities.basic;

import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.util.EnergyUtils;

public class MachineGrinder extends BlockEntityMachine {
    public MachineGrinder() {
        super(2, 1000, EnergyUtils.Bandwidth.BASIC, 2);
    }

    @Override
    public void machineTick() {

    }
}
