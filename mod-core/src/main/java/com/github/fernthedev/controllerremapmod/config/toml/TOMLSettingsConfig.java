package com.github.fernthedev.controllerremapmod.config.toml;

import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.config.SettingsConfigBase;
import lombok.Getter;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class TOMLSettingsConfig extends SettingsConfigBase {

    private ForgeConfigSpec.IntValue attackTimerTicksConfig;

    private ForgeConfigSpec.DoubleValue sensitivityConfig;

    private ForgeConfigSpec.DoubleValue deadzoneLeftConfig;
    private ForgeConfigSpec.DoubleValue deadzoneRightConfig;

    private ForgeConfigSpec.IntValue dropSpeedConfig;
    private ForgeConfigSpec.IntValue scrollSpeedConfig;

    private ForgeConfigSpec.ConfigValue<String> selectedMappingConfig;

    @Getter
    private ModConfig modConfig;

    private Runnable onFirstLoad;


    public TOMLSettingsConfig(ForgeConfigSpec.Builder builder, Runnable onFirstLoad) {
        super(builder);
        this.onFirstLoad = onFirstLoad;
        build(builder);
    }


    public void build(ForgeConfigSpec.Builder builder) {
        builder.push(MAIN_CATEGORY);

        attackTimerTicksConfig =  builder.comment("The time in ticks it takes to launch another attack").defineInRange("attackTimerTicks",5,5,40);

        sensitivityConfig = builder.comment("The sensitivity of the controller").defineInRange("sensitivity",1.0,0.01,5.0);

        deadzoneLeftConfig = builder.comment("The deadzone of the left stick").defineInRange("deadzoneLeft",0.15,0.01,1);
        deadzoneRightConfig = builder.comment("The deadzone of the right stick").defineInRange("deadzoneRight",0.15,0.01,1);


        selectedMappingConfig = builder.comment("The controller mapping that should be used").define("selectedMapping","xboxone");

        scrollSpeedConfig = builder.comment("The speed which bumpers scroll between hotbar").defineInRange("scrollSpeed",6,1,100);
        dropSpeedConfig = builder.comment("The speed which button (B) drops items.").defineInRange("dropSpeed",24,1,100);


        builder.pop();

        //buildDirectoryMappings();

        builder.build();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener((ModConfig.ModConfigEvent event) -> {
            new RuntimeException("Got config " + event.getConfig() + " name " + event.getConfig().getModId() + ":" + event.getConfig().getFileName());
            final ModConfig config = event.getConfig();
            if (config.getSpec() == ConfigHandler.getCLIENT_SPEC()) {
                load(config);
            }

            if(onFirstLoad != null) {
                onFirstLoad.run();
                onFirstLoad = null;
            }
        });
    }

    private void load(ModConfig config) {
        this.modConfig = config;

        attackTimerTicks = attackTimerTicksConfig.get();

        sensitivity = sensitivityConfig.get();

        deadzoneLeft = deadzoneLeftConfig.get();
        deadzoneRight = deadzoneRightConfig.get();

        scrollSpeed = scrollSpeedConfig.get();
        dropSpeed = dropSpeedConfig.get();

        reloadMappings();
    }

    public void reloadMappings() {
        loadedMappingList.clear();
        File dir = new File(FMLPaths.CONFIGDIR.get().toFile(),"mappings");

        if(!dir.exists()) {
            dir.mkdir();
        }

        String fileMapping = selectedMappingConfig.get();

        if(dir.isDirectory() && dir.listFiles() != null) {


            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if(!FilenameUtils.isExtension(file.getName(),"mapping")) continue;

                if(FilenameUtils.removeExtension(file.getName()).equalsIgnoreCase("template")) continue;

                if(file.isDirectory()) continue;

                MappingConfig config = MappingConfig.loadConfig(file);

                loadedMappingList.add(config);

                if(FilenameUtils.removeExtension(file.getName()).equalsIgnoreCase(fileMapping)) {
                    selectedMapping = config;
                }
            }
        }
    }

    @Override
    public void save() {
        setAndSave(attackTimerTicksConfig.getPath(), attackTimerTicks);
        setAndSave(sensitivityConfig.getPath(),sensitivity);
        setAndSave(selectedMappingConfig.getPath(),FilenameUtils.removeExtension(selectedMapping.getFile().getName()));
        setAndSave(deadzoneLeftConfig.getPath(), deadzoneLeft);
        setAndSave(deadzoneRightConfig.getPath(), deadzoneRight);
        setAndSave(dropSpeedConfig.getPath(), dropSpeed);
        setAndSave(scrollSpeedConfig.getPath(), scrollSpeed);
    }

    @Override
    public void sync() {
        save();
        reloadMappings();
    }

    public void setAndSave(String path, Object value) {
        modConfig.getConfigData().set(path,value);
        modConfig.save();
    }

    public void setAndSave(List<String> path, Object value) {
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
