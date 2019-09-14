package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.*;

@Data
@Setter
@RequiredArgsConstructor
public class ControllerButtonState {
    @Setter(AccessLevel.NONE)
    private boolean isHeld;

    void setHeld(boolean held) {
        if(held != this.isHeld) {
            pressed = 0;
        }
        this.isHeld = held;
    }

    @NonNull
    @Setter(AccessLevel.MODULE)
    private int buttonIndex;

    @Getter(AccessLevel.NONE)
    private int pressed = 0;

    public boolean isPressed() {
        if(pressed == 0 && isHeld) {
            pressed--;
            return true;
        } else {
            return false;
        }
    }
}
