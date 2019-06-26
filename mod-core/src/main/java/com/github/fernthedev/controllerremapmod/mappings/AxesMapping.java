package com.github.fernthedev.controllerremapmod.mappings;

import com.github.fernthedev.controllerremapmod.mappings.gson.GsonAxeMapping;
import com.google.gson.Gson;

public interface AxesMapping {

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    public abstract int getHORIZONTAL_LEFT_STICKER();

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    public abstract int getVERTICAL_LEFT_STICKER();

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    public abstract int getVERTICAL_RIGHT_STICKER();

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    public abstract int getHORIZONTAL_RIGHT_STICKER();

    /**
     * -1 is default
     * 1 is trigger
     */
    public abstract int getLEFT_TRIGGER();

    /**
     * 0 is default
     * 1 is trigger
     */
    public abstract int getRIGHT_TRIGGER();

    default String toJson() {
        return new Gson().toJson(this);
    }

    default GsonAxeMapping toGson() {
        return new Gson().fromJson(toJson(),GsonAxeMapping.class);
    }
}
