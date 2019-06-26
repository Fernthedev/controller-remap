package com.github.fernthedev.controllerremapmod;

import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;


// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
@AllArgsConstructor
public class MyEventHandler {

    private ControllerRemapModMain controllerRemapModMain;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        controllerRemapModMain.getControllerHandler().updateTick(new ControlPlayer(Minecraft.getInstance().player));
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        controllerRemapModMain.getControllerHandler().render(new ControlPlayer(Minecraft.getInstance().player));
    }

    @SubscribeEvent
    public void onInputCheck(InputUpdateEvent e) {
        controllerRemapModMain.getControllerHandler().moveEvent(e.getMovementInput(),new ControlPlayer(Minecraft.getInstance().player));
    }

}
