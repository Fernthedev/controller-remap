package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.Data;
import lombok.Setter;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

@Data
@Setter
public class ControllerButtons {

    private ControllerButtonState A = new ControllerButtonState(0);
    private ControllerButtonState B = new ControllerButtonState(1);
    private ControllerButtonState X = new ControllerButtonState(2);
    private ControllerButtonState Y = new ControllerButtonState(3);

    private ControllerButtonState BUMPER_LEFT = new ControllerButtonState(4);
    private ControllerButtonState BUMPER_RIGHT = new ControllerButtonState(5);

    private ControllerButtonState EXTRA_BUTTON = new ControllerButtonState(6);
    private ControllerButtonState START_BUTTON = new ControllerButtonState(7);

    private ControllerButtonState LEFT_STICKER = new ControllerButtonState(8);
    private ControllerButtonState RIGHT_STICKER = new ControllerButtonState(9);

    private ControllerButtonState DPAD_UP = new ControllerButtonState(10);
    private ControllerButtonState DPAD_RIGHT = new ControllerButtonState(11);
    private ControllerButtonState DPAD_DOWN = new ControllerButtonState(12);
    private ControllerButtonState DPAD_LEFT = new ControllerButtonState(13);

    public static ControllerButtons getControllerButtons(int controllerIndex) {
        ControllerButtons controllerButtons = new ControllerButtons();
        ByteBuffer buttons = glfwGetJoystickButtons(controllerIndex);

        int buttonID = 1;
        assert buttons != null;
        while (buttons.hasRemaining()) {
            int state = buttons.get();
            boolean pressed = state == GLFW_PRESS;

            switch (buttonID - 1) {
                case 0:
                    controllerButtons.A.setState(pressed);
                    break;
                case 1:
                    controllerButtons.B.setState(pressed);
                    break;
                case 2:
                    controllerButtons.X.setState(pressed);
                    break;
                case 3:
                    controllerButtons.Y.setState(pressed);
                    break;
                case 4:
                    controllerButtons.BUMPER_LEFT.setState(pressed);
                    break;
                case 5:
                    controllerButtons.BUMPER_RIGHT.setState(pressed);
                    break;
                case 6:
                    controllerButtons.EXTRA_BUTTON.setState(pressed);
                    break;
                case 7:
                    controllerButtons.START_BUTTON.setState(pressed);
                    break;
                case 8:
                    controllerButtons.LEFT_STICKER.setState(pressed);
                    break;
                case 9:
                    controllerButtons.RIGHT_STICKER.setState(pressed);
                    break;
                case 10:
                    controllerButtons.DPAD_UP.setState(pressed);
                    break;
                case 11:
                    controllerButtons.DPAD_RIGHT.setState(pressed);
                    break;
                case 12:
                    controllerButtons.DPAD_DOWN.setState(pressed);
                    break;
                case 13:
                    controllerButtons.DPAD_LEFT.setState(pressed);
                    break;

            }

            buttonID++;
        }
        return controllerButtons;
    }

}
