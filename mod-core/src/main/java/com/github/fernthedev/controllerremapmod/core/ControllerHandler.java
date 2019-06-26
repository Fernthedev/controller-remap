package com.github.fernthedev.controllerremapmod.core;

import com.github.fernthedev.controllerremapmod.config.IConfigHandler;
import com.github.fernthedev.controllerremapmod.config.ISettingsConfig;
import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.core.joystick.ControllerButtonState;
import com.github.fernthedev.controllerremapmod.core.joystick.ControllerButtons;
import com.github.fernthedev.controllerremapmod.core.joystick.JoystickController;
import com.github.fernthedev.controllerremapmod.mappings.ps4.DS4Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import lombok.Getter;
import net.minecraft.util.MovementInput;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class ControllerHandler {

    private static JoystickController controller;

    @Getter
    private static IHandler handler;

    @Getter
    private static IConfigHandler configHandler;

    public static void setHandler(IHandler newHandler) {
        if(handler == null) {
            handler = newHandler;
            handler.setControllerHandler(new ControllerHandler());

            configHandler = handler.getConfigHandler();

            handler.getLogger().debug("The controller mapping is " + configHandler.getSettings());

            controller = new JoystickController(0,configHandler.getSettings().getSelectedMapping());
        }else{
            throw new IllegalStateException("Handler has been set already");
        }
    }

    public static void createMappingTemplates(File dir) {
        if(!dir.exists()) {
            dir.mkdir();
        }

        File mapFile = new File(dir, "template.json");

        MappingConfig mappingConfig = new MappingConfig(mapFile,new XboxOneMapping());
        mappingConfig.load();
        ///////////////////////////////////////////
        mapFile = new File(dir, "xboxone.json");

        mappingConfig = new MappingConfig(mapFile,new XboxOneMapping());
        mappingConfig.load();
        ////////////////////////////////////
        mapFile = new File(dir, "dualshock4.json");

        mappingConfig = new MappingConfig(mapFile,new DS4Mapping());
        mappingConfig.load();
    }

    {
        if(handler == null) throw new IllegalStateException("Please register a handler using the static method setHandler(IHandler)");
    }

    public static Logger getLogger() {
        return handler.getLogger();
    }

    private ControllerButtons oldButtons;

    private ControllerButtons oldMoveButtons;

    private int scrollTime;
    private int maxScrollTime = 6;

    private int dropTime;


    private int rightClickTimeDelay;
    private int leftClickTimeDelay;



    private int leftClickPressTimeHeld;
    private int rightClickPressTimeHeld;

    private boolean sneakToggleButton;

    private double leftDeadzone = 0.3;
    private boolean sneak;

    public void moveEvent(MovementInput event,IControlPlayer player) {
        glfwPollEvents();
        if(!controller.isConnected()) return;

        updateSettings();

        if(oldMoveButtons == null) {
            oldMoveButtons = controller.getButtons();
        }

        if(!handler.isGuiOpen()) {

            if (controller.getAxes().getVERTICAL_LEFT_STICKER().getValue() > leftDeadzone || controller.getAxes().getVERTICAL_LEFT_STICKER().getValue() < -leftDeadzone)
                event.moveForward = -1 * controller.getAxes().getVERTICAL_LEFT_STICKER().getValue();

            if (controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue() > leftDeadzone || controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue() < -leftDeadzone)
                event.moveStrafe = -1 * controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue();

            if (controller.getButtons().getA().isState()) {
                event.jump = controller.getButtons().getA().isState();
            }

            if(!player.isFlying()) {
                if (controller.getButtons().getLEFT_STICKER().isState()) {

                    if (!sneakToggleButton) {
                        sneakToggleButton = true;
                        sneak = !sneak;
                    }

                } else {
                    sneakToggleButton = false;
                }

                if(sneak)
                event.sneak = true;


                if (this.sneak) {
                    event.moveStrafe = (float) ((double) event.moveStrafe * 0.3D);
                    event.moveForward = (float) ((double) event.moveForward * 0.3D);
                }
            }else{
                if(controller.getButtons().getLEFT_STICKER().isState()) {
                    event.sneak = controller.getButtons().getLEFT_STICKER().isState();
                }
            }

            if(controller.getAxes().getLEFT_TRIGGER().getValue() < 0.5) {
                rightClickPressTimeHeld = 1;
            }

            if(controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5) {
                if(rightClickPressTimeHeld >= -1) {
                    rightClickPressTimeHeld--;
                }
            }

            boolean rightClickPress = rightClickPressTimeHeld == 0;

            if(rightClickPress) {
                handler.clickRightMouse();
                rightClickTimeDelay = 8;
            }

            if(rightClickTimeDelay == 0 && !player.isHandActive() && controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5 ) {
                handler.clickRightMouse();
                rightClickTimeDelay = 8;
            }

//            handler.printChat(rightClickTimeDelay + " is the delay called from " + called);




            if(controller.getAxes().getRIGHT_TRIGGER().getValue() < 0.5) {
                leftClickPressTimeHeld = 1;
            }

            if(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5) {
                if(leftClickPressTimeHeld >= -1) {
                    leftClickPressTimeHeld--;
                }
            }

            boolean leftClickPress = leftClickPressTimeHeld == 0;

            if(player.staringAtMob() && controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5 && leftClickTimeDelay <= 0) leftClickPress = true;

            handler.clickMouse(leftClickPress,controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5);

            if(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5 && leftClickTimeDelay <= 0) {
                leftClickTimeDelay = 10;
            }



            ////////////////////////////////
            //Scrolls between the hotbar
//            if(scrollTime >= 20) {
//                scrollTime = 0;
//            }

            if(controller.getButtons().getBUMPER_RIGHT().isState() ) {
                if(scrollTime == 0 || scrollTime > maxScrollTime) {
                    player.scrollSlot(-1);
                }
                scrollTime++;
            }

            if(controller.getButtons().getBUMPER_LEFT().isState() ) {
                if(scrollTime == 0 || scrollTime > maxScrollTime) {
                    player.scrollSlot(1);
                }
                scrollTime++;
            }

            if(!controller.getButtons().getBUMPER_RIGHT().isState() && !controller.getButtons().getBUMPER_LEFT().isState()) {
                scrollTime = 0;
            }


            ////////////////////////////////
        }











        if(checkToggle(controller.getButtons().getY(),oldMoveButtons.getY())) {
            boolean opened = handler.isInventory();

            if(opened) {
                //Equivalent to (Minecraft.getMinecraft().displayGuiScreen(null);)
                handler.closeGUI();
            }else{
                //Equivalent to (Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(Minecraft.getMinecraft().thePlayer));)
                player.openInventory();
            }
        }

        //Basically does what TAB would do
        if(checkToggle(controller.getButtons().getEXTRA_BUTTON(),oldMoveButtons.getEXTRA_BUTTON())) {
            handler.renderPlayerList(true);
        }else{
            handler.renderPlayerList(false);
        }


        //Basically does (Minecraft.getMinecraft().displayGuiScreen(new GuiChat());)
        if(controller.getButtons().getDPAD_UP().isState()) {
            if(!toggleChatButton) {
                toggleChatButton = true;
                handler.displayChat();
            }
        }else{
            toggleChatButton = false;
        }

        if(controller.getButtons().getRIGHT_STICKER().isState()) {
            if(!toggle3rdPersonButton) {
                toggle3rdPersonButton = true;
                //Basically Minecraft.getInstance().gameSettings.thirdPersonView++;
                handler.toggle3rdPerson();
            }
        }else{
            toggle3rdPersonButton = false;
        }

        boolean resetDroptime = true;

        //////////////////////////////////////////////
        // Drops item
        if(!handler.isGuiOpen()) {

            if (controller.getButtons().getB().isState()) {

                if(dropTime == 0) {
                    player.dropItem();
                }

                if(dropTime > 30) {
                    dropTime = 24;
                    player.dropItem();
                }

                resetDroptime = false;

//                if(dropTime > 50) {
//                    player.dropStack();
//                }

                dropTime++;
            }

            if(resetDroptime) {
                dropTime = 0;
            }


        }
        //////////////////////////////////////////////


        oldMoveButtons = controller.getButtons();


    }

    private boolean toggleChatButton;
    private boolean toggle3rdPersonButton;


    public void render(IControlPlayer player) {
        if(!handler.isGuiOpen()) {

            double deadzoneAmount = 0.3;



            //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationPitch += controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue();)
            if(deadzone(controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue(),-deadzoneAmount,deadzoneAmount))
                player.addRotationPitch((float) (controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue() * updateSettings().getSensitivity())); // -1 is down, 1 is up, 0 is stateless

            if(deadzone(controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue(),-deadzoneAmount,deadzoneAmount))
                //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationYaw += controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue();)
                player.addRotationYaw((float) (controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue() *  updateSettings().getSensitivity())); // -1 IS DOWN, 1 IS UP, 0 IS STATELESS

        }
    }

    private boolean toggleMenuButton;

    public void updateTick(IControlPlayer player) {
        glfwPollEvents();
        if(!controller.isConnected()) return;

        updateSettings();

        if(oldButtons == null) {
            oldButtons = controller.getButtons();
        }

        if(controller.getButtons().getSTART_BUTTON().isState()) {
            if(!toggleMenuButton) {
                toggleMenuButton = true;
                if (handler.isGuiOpen()) {
                    handler.closeGUI();
                } else {
                    handler.openMainMenu();
                }
            }
            //Would execute what Escape does
        }else{
            toggleMenuButton = false;
        }



        //Closes GUI
        if(checkToggle(controller.getButtons().getB(),oldButtons.getB())) {
            if(handler.isGuiOpen()) {
                //Equivalent to (Minecraft.getMinecraft().displayGuiScreen(null);)
                handler.closeGUI();
            }
        }


        leftClickTimeDelay--;

        if(rightClickTimeDelay > 0) {
            rightClickTimeDelay--;
        }



        oldButtons = controller.getButtons();
    }



    private boolean checkToggle(ControllerButtonState newButton, ControllerButtonState oldState) {
        return newButton.isState() != oldState.isState();
    }

    private boolean deadzone(float value, float min, float max) {
        return value > max || value < min;
    }

    private boolean deadzone(float value, double min, double max) {
        return value > max || value < min;
    }

    private ISettingsConfig updateSettings() {
        controller.setMapping(handler.getConfigHandler().getSettings().getSelectedMapping());
        return handler.getConfigHandler().getSettings();
    }
}
