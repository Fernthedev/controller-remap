package com.github.fernthedev.controllerremapmod.mappings;

import com.github.fernthedev.controllerremapmod.mappings.gson.GsonButtonMapping;
import com.google.gson.Gson;

public interface ButtonMapping {


    public abstract int getA();

    public abstract int getB();

    public abstract int getX();

    public abstract int getY();

    public abstract int getBUMPER_LEFT();

    public abstract int getBUMPER_RIGHT();

    public abstract int getEXTRA_BUTTON();

    public abstract int getSTART_BUTTON() ;

    public abstract int getLEFT_STICKER();
    public abstract int getRIGHT_STICKER();

    public abstract int getDPAD_UP();

    public abstract int getDPAD_RIGHT();

    public abstract int getDPAD_DOWN();

    public abstract int getDPAD_LEFT();

    default String toJson() {
        return new Gson().toJson(this);
    }

    default GsonButtonMapping toGson() {
        return new Gson().fromJson(toJson(), GsonButtonMapping.class);
    }
}
