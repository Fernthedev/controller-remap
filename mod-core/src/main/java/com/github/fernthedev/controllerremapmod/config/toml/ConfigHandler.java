package com.github.fernthedev.controllerremapmod.config.toml;

import com.github.fernthedev.controllerremapmod.config.IConfigHandler;
import com.github.fernthedev.controllerremapmod.core.IHandler;
import lombok.Getter;
import lombok.NonNull;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler extends IConfigHandler {

    @NonNull
    @Getter
    private static ForgeConfigSpec CLIENT_SPEC;

    @Getter
    private static List<ForgeConfigSpec> loadedMappingSpecList = new ArrayList<>();

    @Getter
    private TOMLSettingsConfig settings;

    public ConfigHandler(IHandler main) {
        if(main == null) throw new RuntimeException("Not null");
    }


    private static Runnable onFirstLoad;

    public static ConfigHandler registerSpec(Runnable onFirstLoad) {
        final Pair<ConfigHandler, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigHandler::new);
        CLIENT_SPEC = specPair.getRight();

        ConfigHandler.onFirstLoad = onFirstLoad;

        return specPair.getLeft();
    }


    private ConfigHandler(ForgeConfigSpec.Builder builder) {
        settings = new TOMLSettingsConfig(builder, onFirstLoad);

    }


}
