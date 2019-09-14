package com.github.fernthedev.controllerremapmod.joystick;

import lombok.*;

@Data
@Setter
@RequiredArgsConstructor
public class ControllerButtonState {
    @Setter(AccessLevel.NONE)
    private boolean held;

    void setHeld(boolean held) {
        if(held != this.held) {
            pressed = 0;
        }
        this.held = held;
    }

    @NonNull
    @Setter(AccessLevel.MODULE)
    private int buttonIndex;

    @Getter(AccessLevel.NONE)
    private int pressed = 0;

    public boolean isPressed() {
        if(pressed == 0 && held) {
            pressed--;
            return true;
        } else {
            return false;
        }
    }
}
