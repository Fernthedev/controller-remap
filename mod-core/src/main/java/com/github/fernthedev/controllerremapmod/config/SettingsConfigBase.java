package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.common.ForgeConfigSpec;

@Data
@RequiredArgsConstructor
public abstract class SettingsConfigBase implements ISettingsConfig {

    protected SettingsConfigBase(ForgeConfigSpec.Builder builder) {}

    public static final String MAIN_CATEGORY = "Controller";


    public static final String MAPPING_CATEGORY = "Mapping";

    protected double sensitivity = 1.0;
    protected Mapping selectedMapping = new XboxOneMapping();

    public abstract void save();
}
