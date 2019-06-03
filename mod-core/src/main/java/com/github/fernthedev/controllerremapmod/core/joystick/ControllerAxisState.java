package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@RequiredArgsConstructor
public class ControllerAxisState {
    private float value;
    private final int buttonIndex;


    public boolean equals(ControllerAxisState obj) {
        return (this.value == obj.value && buttonIndex == obj.buttonIndex);
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof ControllerAxisState) return equals((ControllerAxisState) object);
        else return super.equals(object);
    }
}
