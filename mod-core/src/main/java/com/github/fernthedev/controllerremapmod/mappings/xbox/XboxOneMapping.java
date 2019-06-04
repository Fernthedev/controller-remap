package com.github.fernthedev.controllerremapmod.mappings.xbox;

import com.github.fernthedev.controllerremapmod.mappings.AxesMapping;
import com.github.fernthedev.controllerremapmod.mappings.ButtonMapping;
import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import lombok.Getter;

@Getter
public class XboxOneMapping extends Mapping {

    @Override
    public AxesMapping getAxesMapping() {
        return XboxOneAxeMapping.INSTANCE();
    }

    @Override
    public ButtonMapping getButtonMapping() {
        return XboxOneButtonMapping.INSTANCE();
    }
}
