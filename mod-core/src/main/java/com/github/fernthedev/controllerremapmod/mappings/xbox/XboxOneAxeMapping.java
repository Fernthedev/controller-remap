package com.github.fernthedev.controllerremapmod.mappings.xbox;

import com.github.fernthedev.controllerremapmod.mappings.AxesMapping;


public class XboxOneAxeMapping implements AxesMapping {

    private static XboxOneAxeMapping instance;


    public static AxesMapping INSTANCE() {
        if(instance == null) {
            instance = new XboxOneAxeMapping();
        }
        return instance;
    }

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    @Override
    public int getHORIZONTAL_LEFT_STICKER() {
        return 0;
    }

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    @Override
    public int getVERTICAL_LEFT_STICKER() {
        return 1;
    }

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    @Override
    public int getVERTICAL_RIGHT_STICKER() {
        return 2;
    }

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    @Override
    public int getHORIZONTAL_RIGHT_STICKER() {
        return 3;
    }

    /**
     * -1 is default
     * 1 is trigger
     */
    @Override
    public int getLEFT_TRIGGER() {
        return 4;
    }

    /**
     * 0 is default
     * 1 is trigger
     */
    @Override
    public int getRIGHT_TRIGGER() {
        return 5;
    }



}
