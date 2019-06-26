package com.github.fernthedev.controllerremapmod.mappings.gson;

import com.github.fernthedev.controllerremapmod.mappings.AxesMapping;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class GsonAxeMapping implements AxesMapping {

    @Getter(AccessLevel.NONE)
    private static GsonAxeMapping instance;

    public static AxesMapping INSTANCE() {
        if(instance == null) {
            instance = new GsonAxeMapping();
        }
        return instance;
    }

    private final int HORIZONTAL_LEFT_STICKER = 0;
    private final int VERTICAL_LEFT_STICKER = 1;
    private final int VERTICAL_RIGHT_STICKER = 2;
    private final int HORIZONTAL_RIGHT_STICKER = 3;
    private final int LEFT_TRIGGER = 4;
    private final int RIGHT_TRIGGER = 5;


}
