package com.github.fernthedev.controllerremapmod.mappings;

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


}
