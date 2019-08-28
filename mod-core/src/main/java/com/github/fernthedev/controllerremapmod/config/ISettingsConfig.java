package com.github.fernthedev.controllerremapmod.config;

import java.util.List;

public interface ISettingsConfig {

    MappingConfig getSelectedMapping();

    void setSelectedMapping(MappingConfig mapping);

    List<MappingConfig> getLoadedMappingList();

    double getSensitivity();

    void setSensitivity(double sensitivity);

    double getDeadzoneLeft();
    double getDeadzoneRight();

    int getScrollSpeed();
    void setScrollSpeed(int scrollSpeed);

    int getDropSpeed();
    void setDropSpeed(int dropSpeed);

    int getAttackTimerTicks();
    void setAttackTimerTicks(int attackTimerTicks);

    void save();

    void sync();

    void setDeadzoneLeft(double deadzoneLeft);

    void setDeadzoneRight(double deadzoneRight);
}
