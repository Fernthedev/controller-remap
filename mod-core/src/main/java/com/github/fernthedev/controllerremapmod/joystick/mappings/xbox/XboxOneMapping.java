package com.github.fernthedev.controllerremapmod.joystick.mappings.xbox;

import com.github.fernthedev.controllerremapmod.joystick.mappings.Mapping;
import lombok.Getter;

@Getter
public class XboxOneMapping extends Mapping {

    public XboxOneMapping() {
        super(XboxOneButtonMapping.INSTANCE(), XboxOneAxeMapping.INSTANCE(),"XboxOne");
    }


}
