package com.github.fernthedev.controllerremapmod.mappings.xbox;

import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import lombok.Getter;

@Getter
public class XboxOneMapping extends Mapping {

    public XboxOneMapping() {
        super(XboxOneButtonMapping.INSTANCE(),XboxOneAxeMapping.INSTANCE(),"XboxOne");
    }


}
