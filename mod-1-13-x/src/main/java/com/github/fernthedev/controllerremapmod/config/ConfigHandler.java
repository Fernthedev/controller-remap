package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.ControllerRemapModMain;
import lombok.Getter;
import lombok.NonNull;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigHandler extends IConfigHandler {

    @NonNull
    @Getter
    private static ForgeConfigSpec CLIENT_SPEC;

    @Getter
    private static List<ForgeConfigSpec> loadedMappingSpecList = new ArrayList<>();

    private List<MappingConfig> mappingConfigs;



    @Override
    public SettingsConfigBase getSettings() {
        return settingsConfig;
    }

    @Override
    public void setSettings(SettingsConfigBase settingsConfigBase) {
        this.settingsConfig = (TOMLSettingsConfig) settingsConfigBase;
    }

    private TOMLSettingsConfig settingsConfig;

    public ConfigHandler(File configFile) {
        super(configFile);
    }

    @Override
    protected SettingsConfigBase buildSettings() {
        if(settingsConfig == null) {
            settingsConfig = new TOMLSettingsConfig();
        }
        return settingsConfig;
    }

    public ConfigHandler(ControllerRemapModMain main) {
        if(main == null) throw new RuntimeException("Not null");
    }

    @Override
    public void sync() {
        settingsConfig.getModConfig().save();
        reloadMappings();

    }

    public void reloadMappings() {
        mappingConfigs.clear();
        File dir = new File(FMLPaths.CONFIGDIR.get().toFile(),"mappings");

        if(!dir.exists()) {
            dir.mkdir();
        }



        if(dir.isDirectory() && dir.listFiles() != null) {

            for(File file : Objects.requireNonNull(dir.listFiles())) {
                if(file.isDirectory()) continue;

                mappingConfigs.add(MappingConfig.loadConfig(file));
//                ModConfig tempConfig = new ModConfig(ModConfig.Type.CLIENT,spec,(ModContainer) ControllerHandler.getHandler().getModContainer(),ControllerHandler.getHandler().getModID() + "/" + file.getName() );

//                tempConfig.save();
            }
        }
    }

    @Override
    protected void load() {
        super.load();
    }

    public static ConfigHandler registerSpec() {
        final Pair<ConfigHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigHandler::new);
        CLIENT_SPEC = specPair.getRight();
        return specPair.getLeft();
    }


    private ConfigHandler(ForgeConfigSpec.Builder builder) {
        settingsConfig = new TOMLSettingsConfig();

        settingsConfig.build(builder);
    }
}
