package com.github.fernthedev.controllerremapmod;

import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiButtonExt;
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

    @SubscribeEvent
    public void onGuiInitEvent(GuiScreenEvent.InitGuiEvent e) {
        if (e.getGui() instanceof GuiOptions) {
            GuiOptions options = (GuiOptions) e.getGui();

            int maxY = 0;
            int id = 0;

            for (final GuiButton button : e.getButtonList()) {
                maxY = Math.max(button.y, maxY);
                id = Math.max(button.id, id);
            }

            id++;

            e.addButton(new GuiButtonExt(id , options.width / 2 - 155, maxY + 24,"Controller Options") {
                /**
                 * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
                 *
                 * @param mouseX MouseX
                 * @param mouseY MouseY
                 */
                @Override
                public void onClick(double mouseX, double mouseY) {
                    super.onClick(mouseX, mouseY);
                    controllerRemapModMain.displayOptions();
                }
            });
        }
    }

}
