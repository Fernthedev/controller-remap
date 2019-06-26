package com.github.fernthedev.controllerremapmod.config.toml;

import com.github.fernthedev.controllerremapmod.config.IConfigHandler;
import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.core.IHandler;
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

    private List<MappingConfig> mappingConfigs = new ArrayList<>();

    @Getter
    private TOMLSettingsConfig settings;

    public ConfigHandler(IHandler main) {
        if(main == null) throw new RuntimeException("Not null");
    }

    @Override
    public void sync() {
        save();
        reloadMappings();

    }

    @Override
    protected void save() {
        settings.getModConfig().save();
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
            }
        }
    }

    public static ConfigHandler registerSpec() {
        final Pair<ConfigHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigHandler::new);
        CLIENT_SPEC = specPair.getRight();
        return specPair.getLeft();
    }


    private ConfigHandler(ForgeConfigSpec.Builder builder) {
        settings = new TOMLSettingsConfig(builder);

    }
}
