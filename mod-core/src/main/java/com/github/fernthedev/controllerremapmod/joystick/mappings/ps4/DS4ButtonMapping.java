package com.github.fernthedev.controllerremapmod.joystick.mappings.ps4;

import com.github.fernthedev.controllerremapmod.joystick.mappings.ButtonMapping;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class DS4ButtonMapping implements ButtonMapping {
    
    @Getter(AccessLevel.NONE)
    private static DS4ButtonMapping instance;

    public static ButtonMapping INSTANCE() {
        if(instance == null) {
            instance = new DS4ButtonMapping();
        }
        return instance;
    }
    
    private final int A = 1;
    private final int B = 2;
    private final int X = 0;
    private final int Y = 3;
    
    private final int BUMPER_LEFT = 4;
    private final int BUMPER_RIGHT = 5;
    
    private final int EXTRA_BUTTON = 13;
    private final int START_BUTTON = 9;
    
    private final int LEFT_STICKER = 10;
    private final int RIGHT_STICKER = 11;
    
    public final int DPAD_UP = 14;
    public final int DPAD_RIGHT = 15;
    public final int DPAD_DOWN = 16;
    public final int DPAD_LEFT = 17;

    
}
