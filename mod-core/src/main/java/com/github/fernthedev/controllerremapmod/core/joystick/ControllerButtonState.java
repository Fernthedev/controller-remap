package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.*;

@Data
@Setter
@RequiredArgsConstructor
public class ControllerButtonState {
    private boolean state;
    private final int buttonIndex;
}
