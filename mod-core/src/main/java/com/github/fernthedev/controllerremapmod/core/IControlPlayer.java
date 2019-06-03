package com.github.fernthedev.controllerremapmod.core;

public interface IControlPlayer {
    void moveForward(float value);

    void moveStrafing(float value);

    void addRotationYaw(float value);

    void addRotationPitch(float value);

    void openInventory();

    void toggleSneak();

    void scrollSlot(int amount);

    void dropItem();

    void dropStack();

    boolean isSneak();

    boolean isHandActive();

    boolean staringAtMob();

    boolean isCreative();

    boolean isFlying();
}
