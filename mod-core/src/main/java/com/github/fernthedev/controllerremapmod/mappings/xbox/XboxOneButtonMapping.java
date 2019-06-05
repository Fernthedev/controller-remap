package com.github.fernthedev.controllerremapmod.mappings.xbox;

import com.github.fernthedev.controllerremapmod.mappings.ButtonMapping;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class XboxOneButtonMapping implements ButtonMapping {
    
    @Getter(AccessLevel.NONE)
    private static XboxOneButtonMapping instance;

    public static ButtonMapping INSTANCE() {
        if(instance == null) {
            instance = new XboxOneButtonMapping();
        }
        return instance;
    }
    
    private final int A = 0;
    private final int B = 1;
    private final int X = 2;
    private final int Y = 3;
    
    private final int BUMPER_LEFT = 4;
    private final int BUMPER_RIGHT = 5;
    
    private final int EXTRA_BUTTON = 6;
    private final int START_BUTTON = 7;
    
    private final int LEFT_STICKER = 8;
    private final int RIGHT_STICKER = 9;
    
    public final int DPAD_UP = 10;
    public final int DPAD_RIGHT = 11;
    public final int DPAD_DOWN = 12;
    public final int DPAD_LEFT = 13;

    
}
