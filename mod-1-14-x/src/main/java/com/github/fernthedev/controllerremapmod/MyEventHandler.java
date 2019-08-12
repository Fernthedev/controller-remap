package com.github.fernthedev.controllerremapmod;

import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.Mod;


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

    @SubscribeEvent
    public void onGuiInitEvent(GuiScreenEvent.InitGuiEvent e) {
        if (e.getGui() instanceof OptionsScreen) {
            OptionsScreen options = (OptionsScreen) e.getGui();

            int maxY = 0;

            for (final Widget button : e.getWidgetList()) {
                maxY = Math.max(button.y, maxY);
            }


            e.addWidget(new GuiButtonExt(options.width / 2 - 155, maxY + 24, 150, 20, "Controller Options", new Button.IPressable() {
                @Override
                public void onPress(Button button) {
                    controllerRemapModMain.displayOptions();
                }
            }));
        }
    }

}
