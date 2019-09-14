package com.github.fernthedev.controllerremapmod.joystick.mappings.gson;

import com.github.fernthedev.controllerremapmod.joystick.mappings.ButtonMapping;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class GsonButtonMapping implements ButtonMapping {
    
    @Getter(AccessLevel.NONE)
    private static GsonButtonMapping instance;

    public static ButtonMapping INSTANCE() {
        if(instance == null) {
            instance = new GsonButtonMapping();
        }
        return instance;
    }
    
    private int A;
    private int B;
    private int X;
    private int Y;
    
    private int BUMPER_LEFT;
    private int BUMPER_RIGHT;
    
    private int EXTRA_BUTTON;
    private int START_BUTTON;
    
    private int LEFT_STICKER;
    private int RIGHT_STICKER;
    
    private int DPAD_UP;
    private int DPAD_RIGHT;
    private int DPAD_LEFT;
    private int DPAD_DOWN;

    
}
