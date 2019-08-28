package com.github.fernthedev.controllerremapmod.core;

import com.github.fernthedev.controllerremapmod.config.IConfigHandler;
import com.github.fernthedev.controllerremapmod.config.ISettingsConfig;
import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.config.ui.IConfigGUI;
import com.github.fernthedev.controllerremapmod.core.joystick.ControllerAxis;
import com.github.fernthedev.controllerremapmod.core.joystick.ControllerButtonState;
import com.github.fernthedev.controllerremapmod.core.joystick.ControllerButtons;
import com.github.fernthedev.controllerremapmod.core.joystick.JoystickController;
import com.github.fernthedev.controllerremapmod.mappings.ps4.DS4Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import lombok.Getter;
import net.minecraft.util.MovementInput;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class ControllerHandler {

    private static JoystickController controller;

    @Getter
    private static IHandler handler;

    @Getter
    private static IConfigHandler configHandler;
    private int maxDropTime;


    public static void setHandler(IHandler newHandler) {
        if(handler == null) {
            handler = newHandler;
            handler.setControllerHandler(new ControllerHandler());

            configHandler = handler.getConfigHandler();

        }else{
            throw new IllegalStateException("Handler has been set already");
        }
    }

    public void init() {
        handler.getLogger().debug("The controller mapping is " + configHandler.getSettings());

        controller = new JoystickController(0, configHandler.getSettings().getSelectedMapping().getMapping());
    }

    public static void createMappingTemplates(File dir) {
        if(!dir.exists()) {
            dir.mkdir();
        }

        File mapFile = new File(dir, "template.mapping");

        MappingConfig mappingConfig = new MappingConfig(mapFile,new XboxOneMapping());
        mappingConfig.load();
        ///////////////////////////////////////////
        mapFile = new File(dir, "xboxone.mapping");

        mappingConfig = new MappingConfig(mapFile,new XboxOneMapping());
        mappingConfig.load();
        ////////////////////////////////////
        mapFile = new File(dir, "dualshock4.mapping");

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
    private ControllerAxis oldMoveAxes;

    private int scrollTime;
    private int maxScrollTime = 6;
    private int dropTime;


    private boolean bHeldToClose;
    private boolean aHeldToClick;
    private boolean quickMoveToClick;

    private int leftClickPressTimeHeld;
    private int rightClickTimeHeld;
    private int rightClickTimeDelay;
    private int leftClickTimeDelay;
    private int leftClickTimeAttackDelay;

    private boolean sneakToggleButton;
    private boolean sprintToggle;
    private boolean sneak;

    private float lastRenderPartialTick; // Is useless, only kept in case.

    private int leftToggleHolder = 0;
    private int rightToggleHolder = 0;

    private boolean renderPlayerList;
    private boolean oldRenderPlayerList;


    public void moveEvent(MovementInput event,IControlPlayer player) {
        if(!controller.isConnected()) return;

        updateSettings();

        if(oldMoveButtons == null || oldMoveAxes == null) {
            oldMoveButtons = controller.getButtons();
            oldMoveAxes = controller.getAxes();
        }

        boolean leftClickPress = leftClickPressTimeHeld == 0;
        boolean shiftPress = false;

        if(!handler.isGuiOpen()) {

            double leftDeadzone = updateSettings().getDeadzoneLeft();

            if (deadzone(controller.getAxes().getVERTICAL_LEFT_STICKER().getValue(), leftDeadzone))
                event.moveForward = -1 * controller.getAxes().getVERTICAL_LEFT_STICKER().getValue();

            if (deadzone(controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue(), leftDeadzone))
                event.moveStrafe = -1 * controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue();

            if (controller.getButtons().getA().isState()) {
                event.jump = controller.getButtons().getA().isState();
            }

            if(!player.isFlying()) {
                if(controller.getButtons().getDPAD_RIGHT().isState()) {
                    sprintToggle = !sprintToggle;
                }

                if (controller.getButtons().getRIGHT_STICKER().isState()) {

                    if (!sneakToggleButton) {
                        sneakToggleButton = true;
                        sneak = !sneak;
                    }

                } else {
                    sneakToggleButton = false;
                }

                if(sneak)
                event.sneak = true;




                if (sneak) {
                    event.moveStrafe = (float) ((double) event.moveStrafe * 0.3D);
                    event.moveForward = (float) ((double) event.moveForward * 0.3D);
                    player.setSprinting(false);
                }


            }else{
                if(controller.getButtons().getRIGHT_STICKER().isState()) {
                    event.sneak = controller.getButtons().getRIGHT_STICKER().isState();
                }
            }


            // Sprinting
            if(sprintToggle && !sneak)
                player.setSprinting(true);


//            handler.printChat(rightClickTimeDelay + " is the delay called from " + called);
// /////////////////////////////////////////

//            if (controller.getAxes().getLEFT_TRIGGER().getValue() < 0.5 && player.isHandActive()) {
//                player.onStoppedUsingItem();
//            }


            // LEFT CLICK
            if(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5 && leftToggleHolder == 0) {
                leftToggleHolder = 1;
                handler.makeClickMouseTrue(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5);
            }

            if (controller.getAxes().getRIGHT_TRIGGER().getValue() < 0.5 && leftToggleHolder == 1) {
                leftToggleHolder = 0;
                handler.makeClickMouseTrue(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5);
            }


            // RIGHT CLICK
            if(controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5 && rightToggleHolder == 0) {
                rightToggleHolder = 1;
                handler.makeRightClickMouseTrue(controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5);
            }

            if (controller.getAxes().getLEFT_TRIGGER().getValue() < 0.5 && rightToggleHolder == 1) {
                rightToggleHolder = 0;
                handler.makeRightClickMouseTrue(controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5);
            }




            ///////////////////////////////// OLD CLICK CODE

//            if (controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5) {
//                player.onStoppedUsingItem();
//            }
//


            if(!player.isObjectMouseOverNull() && player.staringAtMob() && controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5 && leftClickTimeAttackDelay <= 0) {
                leftClickPress = true;
                leftClickTimeAttackDelay = updateSettings().getAttackTimerTicks();
            }

            if(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5 && !player.isObjectMouseOverNull() && player.staringAtAir() && oldMoveAxes.getRIGHT_TRIGGER().getValue() < 0.5) {
                leftClickPress = true;
                leftClickPressTimeHeld = 1;
            }

            if(controller.getAxes().getRIGHT_TRIGGER().getValue() < 0.5) {
                leftClickPress = false;
                leftClickPressTimeHeld = 1;
            }
//
//            if( (!leftClickPress && leftClickPressTimeHeld == 1) || player.isObjectMouseOverNull() || !(player.staringAtBlock())) {
////                player.resetBlockRemoving();
////                leftClickPressTimeHeld++;
//            }
//
//
//
//

//
            if(controller.getAxes().getRIGHT_TRIGGER().getValue() > 0.5 && leftClickTimeDelay <= 0) {
                leftClickTimeDelay = 10;

            }


            ///////////////////////////////// OLD CLICK CODE


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



        if(leftClickPress)
            handler.clickMouse();







        if(isPressed(controller.getButtons().getY(), oldMoveButtons.getY())) {
            boolean opened = handler.isInventory();

            if(!opened) {
                //Equivalent to (Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(Minecraft.getMinecraft().thePlayer));)
                player.openInventory();
            }
        }



        //Basically does what TAB would do
        if(checkToggle(controller.getButtons().getEXTRA_BUTTON(),oldMoveButtons.getEXTRA_BUTTON()) && !handler.isGuiOpen()) {
            renderPlayerList = !renderPlayerList;
        }

        if(renderPlayerList != oldRenderPlayerList && !handler.isGuiOpen()) {
            handler.renderPlayerListTAB(renderPlayerList);
            oldRenderPlayerList = renderPlayerList;
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

        //Basically does (Minecraft.getMinecraft().displayGuiScreen(new GuiChat());)
        if(controller.getButtons().getDPAD_DOWN().isState() && !(handler.getGui() instanceof IConfigGUI)) {
            handler.displayOptions();
        }

        if(controller.getButtons().getLEFT_STICKER().isState()) {
            if(!toggle3rdPersonButton) {
                toggle3rdPersonButton = true;
                //Basically Minecraft.getInstance().gameSettings.thirdPersonView++;
                handler.toggle3rdPerson();
            }
        }else{
            toggle3rdPersonButton = false;
        }

        boolean resetDropTime = true;

        //////////////////////////////////////////////
        // Drops item
        if(!handler.isGuiOpen()) {

            if (controller.getButtons().getB().isState()) {

                if(dropTime == 0) {
                    player.dropItem();
                }

                if(dropTime > maxDropTime + 4) {
                    dropTime = maxDropTime;
                    player.dropItem();
                }

                resetDropTime = false;

//                if(dropTime > 50) {
//                    player.dropStack();
//                }

                dropTime++;
            }

            if(resetDropTime) {
                dropTime = 0;
            }
        }

        lastRenderPartialTick = handler.partialTicks();


        oldMoveButtons = controller.getButtons();
        oldMoveAxes = controller.getAxes();

    }

    private boolean toggleChatButton;
    private boolean toggle3rdPersonButton;


    public void render(IControlPlayer player) {
        if(!controller.isConnected()) return;

        //////////////////////////////////////////////

        if(!handler.isGuiOpen()) {

            double deadzoneAmount = updateSettings().getDeadzoneRight();

            double oldPitch = player.getRotationPitch();
            double oldYaw = player.getRotationYaw();

            double multiplier = updateSettings().getSensitivity() * 4;

            double newPitch = oldPitch + controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue() * multiplier;
            double newYaw = oldYaw + controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue() * multiplier;

            double tick = lastRenderPartialTick; // Is useless, only kept in case.

            double interpolatedPitch = oldPitch + (newPitch - oldPitch) * tick; // Is useless, only kept in case.
            double interpolatedYaw = oldYaw + (newYaw - oldYaw) * tick; // Is useless, only kept in case.

            double velX = newYaw - oldYaw;
            double velY = newPitch - oldPitch;

            boolean setRotation = false;

            //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationPitch += controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue();)
            if (deadzone(controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue(), deadzoneAmount)) {
                setRotation = true;
//                handler.getLogger().debug("Deadzone pitch ("
//                        + deadzone(controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue(), deadzoneAmount) +
//                        ") oldPitch [" + oldPitch + "] + (newPitch [" + newPitch + "]" +
//                        " - oldPitch [" + oldPitch + "]) * handler.partialTicks() [" + tick + "] = " + interpolatedPitch);
//                player.addRotationPitch((float) interpolatedPitch);
            } else {
                interpolatedPitch = (float) oldPitch;
            }
//                player.addRotationPitch((float) (controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue() * updateSettings().getSensitivity())); // -1 is down, 1 is up, 0 is stateless

            if (deadzone(controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue(), deadzoneAmount)) {
                setRotation = true;
//                handler.getLogger().debug("Deadzone yaw ("
//                        + deadzone(controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue(), deadzoneAmount) +
//                        ") oldYaw [" + oldYaw + "] + (newYaw [" + newYaw + "]" +
//                        " - oldYaw [" + oldYaw + "]) * handler.partialTicks() [" + tick + "] = " + interpolatedYaw);
//                player.addRotationYaw((float) interpolatedYaw);
            } else {
                interpolatedYaw = (float) oldYaw;
            }


            if (setRotation)
                player.rotateTowards(velX, velY);
            //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationYaw += controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue();)
//                player.addRotationYaw((float) (controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue() * updateSettings().getSensitivity())); // -1 IS DOWN, 1 IS UP, 0 IS STATELESS

        }

        if (handler.isGuiOpen() && !handler.isMouseGrabbed()) {
            double xCord = handler.getMouseX();
            double yCord = handler.getMouseY();

            double multiplier = 10;

            double deadzoneAmount = updateSettings().getDeadzoneRight();

            //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationPitch += controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue();)
            boolean doMove = false;

            if (deadzone(controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue(), deadzoneAmount)) {
                doMove = true;

                xCord += controller.getAxes().getHORIZONTAL_LEFT_STICKER().getValue() * multiplier;
            }

            if (deadzone(controller.getAxes().getVERTICAL_LEFT_STICKER().getValue(), deadzoneAmount)) {
                doMove = true;

                yCord += controller.getAxes().getVERTICAL_LEFT_STICKER().getValue() * multiplier;
            }


            if(doMove)
                glfwSetCursorPos(handler.getWindowIDGlfw(), xCord, yCord);
        }
//        if(!handler.isGuiOpen()) {
//
//            double deadzoneAmount = updateSettings().getDeadzoneRight();
//
//            //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationPitch += controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue();)
//            if(deadzone(controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue(), deadzoneAmount))
//                player.addRotationPitch((float) (controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue() * updateSettings().getSensitivity())); // -1 is down, 1 is up, 0 is stateless
//
//            if(deadzone(controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue(), deadzoneAmount))
//                //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationYaw += controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue();)
//                player.addRotationYaw((float) (controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue() *  updateSettings().getSensitivity())); // -1 IS DOWN, 1 IS UP, 0 IS STATELESS
//
//        }
    }

    private boolean toggleMenuButton;

    public void updateTick(IControlPlayer player) {
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



        if(handler.isGuiOpen()) {
            //Closes GUI
            if (controller.getButtons().getB().isState()) {
                bHeldToClose = true;
                handler.closeGUI();
            }
        }

        boolean shouldResetToClose = !handler.isGuiOpen() && !controller.getButtons().getB().isState() && bHeldToClose;

        if(shouldResetToClose) bHeldToClose = false;

        if(leftClickTimeDelay > 0) {
            leftClickTimeDelay--;
        }

        if(leftClickTimeAttackDelay > 0) {
            leftClickTimeAttackDelay--;
        }

        if(rightClickTimeDelay > 0) {
            --rightClickTimeDelay;
        }

        if(!handler.isGuiOpen()) {
//
//            double deadzoneAmount = updateSettings().getDeadzoneRight();
//            double multiplier = Math.pow(updateSettings().getSensitivity() * (double) 0.6F + (double) 0.2F, 3) * 0.8D * 10;
//
//            double yaw = 0;
//            double pitch = 0;
//
//            //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationPitch += controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue();)
//            if (deadzone(controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue(), deadzoneAmount)) {
//
//
//                pitch = controller.getAxes().getHORIZONTAL_RIGHT_STICKER().getValue() * multiplier;
////                player.addRotationPitch((float) (val)); // -1 is down, 1 is up, 0 is stateless
//            }
//
//
//            if (deadzone(controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue(), deadzoneAmount)) {
//
//                yaw = controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue() * multiplier;
//
//                //Equivalent to (Minecraft.getMinecraft().thePlayer.rotationYaw += controller.getAxes().getVERTICAL_RIGHT_STICKER().getValue();)
////                player.addRotationYaw((float) val); // -1 IS DOWN, 1 IS UP, 0 IS STATELESS
//            }
//
//            if(yaw == 0) {
//                yaw = player.getRotationYaw();
//            }
//
//            if(pitch == 0) {
//                pitch = player.getRotationPitch();
//            }
//
//            if(yaw != 0 || pitch != 0) {
//                player.setRotation(yaw, pitch);
//            }

            ///////// RIGHT CLICK

            if (controller.getAxes().getLEFT_TRIGGER().getValue() < 0.5) {
                rightClickTimeHeld = 0;
            }


            boolean rightClickPress = rightClickTimeHeld == 0 && controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5; // 0 means true because it has finished waiting for the ticks required.

            if(controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5) { //triggering
                rightClickTimeHeld = 1;
            }

            while (rightClickPress) {
                handler.getLogger().info(rightClickPress + " held: " + rightClickTimeHeld + " delay: " + rightClickTimeDelay + " time " + System.currentTimeMillis());
                rightClickPress = false;
                rightClick(player);
            }




            if (controller.getAxes().getLEFT_TRIGGER().getValue() > 0.5 && rightClickTimeDelay == 0 && !player.isHandActive()) {
                rightClick(player);
                handler.getLogger().info(rightClickPress + " held: " + rightClickTimeHeld + " delay: " + rightClickTimeDelay + " time " + System.currentTimeMillis());
            }

        }



        if(handler.isGuiOpen()) {
            /////////////////////////
            if(!controller.getButtons().getA().isState()) aHeldToClick = false;

            if(controller.getButtons().getA().isState() && !aHeldToClick) {
                aHeldToClick = true;
                double xScale = handler.getMouseX() * (double)handler.getMainWindow().getScaledWidth() / (double)handler.getMainWindow().getWidth();
                double yScale = handler.getMouseY() * (double) handler.getMainWindow().getScaledHeight() / (double)handler.getMainWindow().getHeight();

                handler.mouseClickedScreen(xScale, yScale, GLFW_MOUSE_BUTTON_1);
            }

            ///////////////////////////////////////////

            if(handler.isInventory()) {
                if (!controller.getButtons().getY().isState()) {
                    quickMoveToClick = false;
                }

                if (controller.getButtons().getY().isState() && !quickMoveToClick) {
                    quickMoveToClick = true;



                    double xScale = handler.getMouseX() * (double)handler.getMainWindow().getScaledWidth() / (double)handler.getMainWindow().getWidth();
                    double yScale = handler.getMouseY() * (double) handler.getMainWindow().getScaledHeight() / (double)handler.getMainWindow().getHeight();

                    handler.shiftKeyOn(true);
                    handler.mouseClickedScreen(xScale, yScale, GLFW_MOUSE_BUTTON_1);
                    handler.shiftKeyOn(false);
                }
            }


        }







        oldButtons = controller.getButtons();
    }

    private void rightClick(IControlPlayer player) {
        if (!player.getIsHittingBlock()) {
            rightClickTimeDelay = 6;
            handler.clickRightMouse();
        }
    }



    private boolean checkToggle(ControllerButtonState newButton, ControllerButtonState oldState) {
        return newButton.isState() != oldState.isState();
    }

    private boolean isPressed(ControllerButtonState newButton, ControllerButtonState oldState) {
        return newButton.isState() != oldState.isState() && oldState.isState();
    }

    private boolean deadzone(float value, float amount) {
        return value > amount || value < -amount;
    }

    private boolean deadzone(float value, double amount) {
        return value > amount || value < -amount;
    }

    private ISettingsConfig updateSettings() {
        controller.setMapping(handler.getConfigHandler().getSettings().getSelectedMapping().getMapping());
        maxScrollTime = configHandler.getSettings().getScrollSpeed();
        maxDropTime = configHandler.getSettings().getDropSpeed();

        return handler.getConfigHandler().getSettings();
    }
}
