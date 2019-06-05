package com.github.fernthedev.controllerremapmod.config;


import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import com.github.fernthedev.controllerremapmod.core.IHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public abstract class IConfigHandler {

   protected File configFile;
   protected Configuration config;
   protected IHandler handler;


   @NonNull
   private ControllerHandler controllerHandler;

   public IConfigHandler(File configFile) {
      this.configFile = configFile;
      config = new Configuration(configFile);
      setSettings(buildSettings());
      load();
      sync();
   }

   public abstract SettingsConfigBase getSettings();

   public abstract void setSettings(SettingsConfigBase settingsConfigBase);

   protected abstract SettingsConfigBase buildSettings();

   protected IConfigHandler() {
    }


    public void sync() {
      config.save();
      load();
   }

   protected void load() {

      if(!config.getConfigFile().exists()) {
         try {
            configFile.createNewFile();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      config.load();
      getSettings().parseFromConfig(config);
   }

   @SubscribeEvent
   public void onConfigChange(final ConfigChangedEvent.OnConfigChangedEvent e) {
      try {
         String MODID = handler.getModID();
         if (e.getModID().equals(MODID)) {

            sync();

         }
      } catch (Exception ee) {
         ee.printStackTrace();
      }
   }


}
