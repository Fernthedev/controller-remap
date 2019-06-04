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

@Data
public abstract class SettingsConfigBase {

    public static final String MAIN_CATEGORY = "Controller";


    public static final String MAPPING_CATEGORY = "Mapping";

    protected int sensitivity ;
    protected Mapping selectedMapping;

    protected List<Mapping> loadedMappings;

    public void parseFromConfig(Object configObject) {
        Configuration config = (Configuration) configObject;
        sensitivity = config.getInt("sensitivity",MAIN_CATEGORY,1,-3,3,"The sensitivity of the controller");
        selectedMapping = Mapping.loadFromJSON(config.getString("selectedmapping",MAIN_CATEGORY, new XboxOneMapping().toJson(),"The controller mapping that should be used"));

        File dir = ControllerHandler.getHandler().getConfigDir();

        loadedMappings = new ArrayList<>();

        if(dir.isDirectory() && dir.listFiles() != null) {
            for(File file : dir.listFiles()) {
                Configuration tempConfig = new Configuration(file);
                loadedMappings.add(new Gson().fromJson(tempConfig.getString("mapping", MAPPING_CATEGORY,new XboxOneMapping().toJson(),"A mapping used for controllers."),Mapping.class));
            }
        }
    }


}
