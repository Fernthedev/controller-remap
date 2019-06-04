package com.github.fernthedev.controllerremapmod.mappings.xbox;

import com.github.fernthedev.controllerremapmod.mappings.ButtonMapping;

public class XboxOneButtonMapping implements ButtonMapping {
    private static XboxOneButtonMapping instance;

    public static ButtonMapping INSTANCE() {
        if(instance == null) {
            instance = new XboxOneButtonMapping();
        }
        return instance;
    }

    @Override
    public int getA() {
        return 0;
    }

    @Override
    public int getB() {
        return 1;
    }

    @Override
    public int getX() {
        return 2;
    }

    @Override
    public int getY() {
        return 3;
    }

    @Override
    public int getBUMPER_LEFT() {
        return 4;
    }

    @Override
    public int getBUMPER_RIGHT() {
        return 5;
    }

    @Override
    public int getEXTRA_BUTTON() {
        return 6;
    }

    @Override
    public int getSTART_BUTTON() {
        return 7;
    }

    @Override
    public int getLEFT_STICKER() {
        return 8;
    }

    @Override
    public int getRIGHT_STICKER() {
        return 9;
    }

    @Override
    public int getDPAD_UP() {
        return 10;
    }

    @Override
    public int getDPAD_RIGHT() {
        return 11;
    }

    @Override
    public int getDPAD_DOWN() {
        return 12;
    }

    @Override
    public int getDPAD_LEFT() {
        return 13;
    }
}
