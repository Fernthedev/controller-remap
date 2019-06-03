package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@RequiredArgsConstructor
public class ControllerButtonState {
    private boolean state;
    private final int buttonIndex;

    private boolean equals(ControllerButtonState obj) {
        return (this.state == obj.state && buttonIndex == obj.buttonIndex);
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof ControllerButtonState) return equals((ControllerButtonState) object);
        else return super.equals(object);
    }
}
