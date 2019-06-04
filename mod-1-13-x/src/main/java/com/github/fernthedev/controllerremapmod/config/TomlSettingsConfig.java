package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import com.google.gson.Gson;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TomlSettingsConfig extends SettingsConfigBase {

    private ForgeConfigSpec.IntValue sensitivityConfig;

    private ForgeConfigSpec.ConfigValue<String> selectedMappingConfig;

    private List<ForgeConfigSpec.ConfigValue<String>> mappingListConfig;

    public void build(ForgeConfigSpec.Builder builder) {
        load(builder);


        parseFromConfig(builder);
    }

    private void loadDirectoryMappings() {


        ControllerHandler.getHandler().getLogger().info("The directory is " + ControllerHandler.getHandler().getConfigDir());
        File dir = ControllerHandler.getHandler().getConfigDir();

        loadedMappings = new ArrayList<>();



        if(!dir.exists()) {
            dir.mkdir();
        }

        if(dir.isDirectory() && dir.listFiles() != null) {
            for(File file : Objects.requireNonNull(dir.listFiles())) {
                if(file.isDirectory()) continue;
                final Pair<MappingReader, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MappingReader::new);

                ForgeConfigSpec spec = specPair.getRight();

                ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,spec,"/mappings/" + file.getName());

//                ModConfig tempConfig = new ModConfig(ModConfig.Type.CLIENT,spec,(ModContainer) ControllerHandler.getHandler().getModContainer(),ControllerHandler.getHandler().getModID() + "/" + file.getName() );

//                tempConfig.save();
            }
        }

    }

    private void load(ForgeConfigSpec.Builder builder) {


        builder.push(MAIN_CATEGORY);

        sensitivityConfig = builder.comment("The sensitivity of the controller").defineInRange("sensitivity",1,-3,-3);

        selectedMappingConfig = builder.comment("The controller mapping that should be used").define("selectedmapping",new XboxOneMapping().toJson());



        builder.pop();


        //loadDirectoryMappings();
    }



    @Override
    public void parseFromConfig(Object configObject) {

        sensitivity = sensitivityConfig.get();

        selectedMapping = Mapping.loadFromJSON(selectedMappingConfig.get());

//        loadDirectoryMappings();

//        loadedMappings = new ArrayList<>();
//
//        for(ForgeConfigSpec.ConfigValue<String> forgeConfigSpec : mappingListConfig) {
//            loadedMappings.add(Mapping.loadFromJSON(forgeConfigSpec.get()));
//        }


    }



    private class MappingReader {

        public MappingReader(ForgeConfigSpec.Builder b) {
            b.push(MAPPING_CATEGORY);

            ForgeConfigSpec.ConfigValue<String> val = b.comment("A mapping used for controllers.").define("mapping",new XboxOneMapping().toJson());

            mappingListConfig.add(val);

            String json = val.get();

//                Configuration tempConfig = new Configuration(file);
            loadedMappings.add(new Gson().fromJson(json,Mapping.class));
            b.pop();
        }
    }

}
