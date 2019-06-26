package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.mappings.Mapping;

public interface ISettingsConfig {

    Mapping getSelectedMapping();

    double getSensitivity();
}
