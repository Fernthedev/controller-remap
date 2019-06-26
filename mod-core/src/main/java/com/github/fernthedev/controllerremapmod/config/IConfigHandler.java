package com.github.fernthedev.controllerremapmod.config;


import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import com.github.fernthedev.controllerremapmod.core.IHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class IConfigHandler {

   protected SettingsConfigBase config;
   protected IHandler handler;


   @NonNull
   private ControllerHandler controllerHandler;

   public IConfigHandler(SettingsConfigBase config) {
      this.config = config;
   }

   public abstract ISettingsConfig getSettings();

   protected IConfigHandler() {
    }

    protected abstract void sync();

   protected abstract void save();


}
