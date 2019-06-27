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

    void save();

    void sync();

    void setDeadzoneLeft(double deadzoneLeft);

    void setDeadzoneRight(double deadzoneRight);
}
