package com.github.fernthedev.controllerremapmod.config.toml;

import com.github.fernthedev.controllerremapmod.config.SettingsConfigBase;
import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import lombok.Getter;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class TOMLSettingsConfig extends SettingsConfigBase {

    private ForgeConfigSpec.IntValue sensitivityConfig;

    private ForgeConfigSpec.ConfigValue<String> selectedMappingConfig;

    @Getter
    private ModConfig modConfig;


    public TOMLSettingsConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        build(builder);
    }


    public void build(ForgeConfigSpec.Builder builder) {
        builder.push(MAIN_CATEGORY);

        sensitivityConfig = builder.comment("The sensitivity of the controller").defineInRange("sensitivity",1,-3,-3);

        selectedMappingConfig = builder.comment("The controller mapping that should be used").define("selectedmapping",new XboxOneMapping().toJson());



        builder.pop();

        //buildDirectoryMappings();

        builder.build();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener((ModConfig.ModConfigEvent event) -> {
//            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());
            final ModConfig config = event.getConfig();
            if (config.getSpec() == ConfigHandler.getCLIENT_SPEC()) {
                load(config);
            }
        });


    }

    private void load(ModConfig config) {
        this.modConfig = config;

        sensitivity = sensitivityConfig.get();

        selectedMapping = Mapping.loadFromJSON(selectedMappingConfig.get());

    }


    @Override
    public void save() {
        modConfig.save();
    }

    public void setAndSave(String path, Object value) {
        modConfig.getConfigData().set(path,value);
        modConfig.save();
    }

    public Object getIfNonNull(String path, Object defVal) {
        if(modConfig.getConfigData().get(path) == null) {
            setAndSave(path,defVal);
            return defVal;
        }
        return modConfig.getConfigData().get(path);
    }

}
