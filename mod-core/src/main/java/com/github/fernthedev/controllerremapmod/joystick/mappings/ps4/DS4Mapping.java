package com.github.fernthedev.controllerremapmod.joystick.mappings.ps4;

import com.github.fernthedev.controllerremapmod.joystick.mappings.Mapping;
import lombok.Getter;

@Getter
public class DS4Mapping extends Mapping {

    public DS4Mapping() {
        super(DS4ButtonMapping.INSTANCE(), DS4AxeMapping.INSTANCE(),"DualShock4");
    }


}
