package com.github.fernthedev.controllerremapmod.config;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public abstract class SettingsConfigBase implements ISettingsConfig {

    protected SettingsConfigBase(ForgeConfigSpec.Builder builder) {}

    public static final String MAIN_CATEGORY = "Controller";


    @Setter
    protected double sensitivity = 1.0;

    @Setter
    protected double deadzoneLeft;

    @Setter
    protected double deadzoneRight;

    @Setter
    protected int scrollSpeed;

    @Setter
    protected int dropSpeed;

    @Setter
    protected MappingConfig selectedMapping;

    @Getter
    protected List<MappingConfig> loadedMappingList = new ArrayList<>();



}
