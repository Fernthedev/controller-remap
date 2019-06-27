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

    private int HORIZONTAL_LEFT_STICKER;
    private int VERTICAL_LEFT_STICKER;
    private int VERTICAL_RIGHT_STICKER;
    private int HORIZONTAL_RIGHT_STICKER;
    private int LEFT_TRIGGER;
    private int RIGHT_TRIGGER;


}
