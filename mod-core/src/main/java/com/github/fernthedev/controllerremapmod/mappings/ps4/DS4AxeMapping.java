package com.github.fernthedev.controllerremapmod.mappings.ps4;

import com.github.fernthedev.controllerremapmod.mappings.AxesMapping;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class DS4AxeMapping implements AxesMapping {

    @Getter(AccessLevel.NONE)
    private static DS4AxeMapping instance;

    public static AxesMapping INSTANCE() {
        if(instance == null) {
            instance = new DS4AxeMapping();
        }
        return instance;
    }

    private final int HORIZONTAL_LEFT_STICKER = 0;
    private final int VERTICAL_LEFT_STICKER = 1;
    private final int VERTICAL_RIGHT_STICKER = 2;
    private final int HORIZONTAL_RIGHT_STICKER = 5;
    private final int LEFT_TRIGGER = 3;
    private final int RIGHT_TRIGGER = 4;


}
