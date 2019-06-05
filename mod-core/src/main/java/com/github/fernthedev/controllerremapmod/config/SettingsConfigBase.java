package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import com.google.gson.Gson;
import lombok.Data;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public abstract class SettingsConfigBase {

    public static final String MAIN_CATEGORY = "Controller";


    public static final String MAPPING_CATEGORY = "Mapping";

    protected double sensitivity = 1.0;
    protected Mapping selectedMapping = new XboxOneMapping();

    protected List<Mapping> loadedMappings = new ArrayList<>();

    public void parseFromConfig(Object configObject) {
        Configuration config = (Configuration) configObject;
        sensitivity = config.get(MAIN_CATEGORY,"sensitivity",1.0,"The sensitivity of the controller").getDouble();
        selectedMapping = Mapping.loadFromJSON(config.getString("selectedmapping",MAIN_CATEGORY, new XboxOneMapping().toJson(),"The controller mapping that should be used"));

        File dir = ControllerHandler.getHandler().getConfigDir().toFile();

        loadedMappings = new ArrayList<>();

        if(!dir.exists()) {
            dir.mkdir();
        }

        if(dir.isDirectory() && dir.listFiles() != null) {
            for(File file : Objects.requireNonNull(dir.listFiles())) {
                if(file.isDirectory()) continue;

                Configuration tempConfig = new Configuration(file);
                loadedMappings.add(new Gson().fromJson(tempConfig.getString("mapping", MAPPING_CATEGORY,new XboxOneMapping().toJson(),"A mapping used for controllers."),Mapping.class));
            }
        }
    }


}
