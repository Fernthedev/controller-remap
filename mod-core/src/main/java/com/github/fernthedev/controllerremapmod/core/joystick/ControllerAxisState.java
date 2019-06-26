package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@RequiredArgsConstructor
public class ControllerAxisState {
    @Getter
    private float value;

    @Getter
    private final int buttonIndex;

}
