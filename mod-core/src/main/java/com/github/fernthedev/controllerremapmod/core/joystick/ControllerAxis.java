package com.github.fernthedev.controllerremapmod.core.joystick;

import lombok.Data;
import lombok.Setter;

import java.nio.FloatBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;

@Data
@Setter
public class ControllerAxis {

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    private ControllerAxisState HORIZONTAL_LEFT_STICKER = new ControllerAxisState(0);

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    private ControllerAxisState VERTICAL_LEFT_STICKER = new ControllerAxisState(1);

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    private ControllerAxisState VERTICAL_RIGHT_STICKER = new ControllerAxisState(2);

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    private ControllerAxisState HORIZONTAL_RIGHT_STICKER = new ControllerAxisState(3);

    /**
     * -1 is default
     * 1 is trigger
     */
    private ControllerAxisState LEFT_TRIGGER = new ControllerAxisState(4);

    /**
     * 0 is default
     * 1 is trigger
     */
    private ControllerAxisState RIGHT_TRIGGER = new ControllerAxisState(5);


    /**
     * Gets an instance of {@link ControllerAxis} for the controller specified
     * @param controllerIndex The controller
     * @return The instance of {@link ControllerAxis}
     */
    public static ControllerAxis getAxis(int controllerIndex) {
        ControllerAxis controllerAxis = new ControllerAxis();
        FloatBuffer axes = glfwGetJoystickAxes(controllerIndex);

        int axisID = 1;
        while (Objects.requireNonNull(axes).hasRemaining()) {
            float state = axes.get();
            switch (axisID - 1) {
                case 0:
                    controllerAxis.HORIZONTAL_LEFT_STICKER.setValue(state);
                    break;
                case 1:
                    controllerAxis.VERTICAL_LEFT_STICKER.setValue(state);
                    break;
                case 2:
                    controllerAxis.VERTICAL_RIGHT_STICKER.setValue(state);
                    break;
                case 3:
                    controllerAxis.HORIZONTAL_RIGHT_STICKER.setValue(state);
                    break;
                case 4:
                    controllerAxis.LEFT_TRIGGER.setValue(state);
                    break;
                case 5:
                    controllerAxis.RIGHT_TRIGGER.setValue(state);
                    break;
                default:
                    System.out.println("Not found axis value for state: " + state + " and value " + (axisID -1));
                    break;
            }
            axisID++;
        }
        return controllerAxis;
    }

    /**
     * Checks if the values are different in any state
     * @param controllerAxis The instance of {@link ControllerAxis} to check
     * @return True if all same, false otherwise
     */
    public boolean checkDifferent(ControllerAxis controllerAxis) {
        return controllerAxis.LEFT_TRIGGER.equals(LEFT_TRIGGER) &&
                controllerAxis.RIGHT_TRIGGER.equals(RIGHT_TRIGGER) &&

                controllerAxis.VERTICAL_LEFT_STICKER.equals(VERTICAL_LEFT_STICKER) &&
                controllerAxis.VERTICAL_RIGHT_STICKER.equals(VERTICAL_RIGHT_STICKER) &&

                controllerAxis.HORIZONTAL_LEFT_STICKER.equals(HORIZONTAL_LEFT_STICKER) &&
                controllerAxis.HORIZONTAL_RIGHT_STICKER.equals(HORIZONTAL_RIGHT_STICKER)
                ;
    }

    public void printDifferent(ControllerAxis controllerAxis) {
        if(!controllerAxis.LEFT_TRIGGER.equals(LEFT_TRIGGER)) {
            System.out.println("Current: " + LEFT_TRIGGER + " Check: " + controllerAxis.LEFT_TRIGGER);
        }

        if(!controllerAxis.RIGHT_TRIGGER.equals(RIGHT_TRIGGER)) {
            System.out.println("Current: " + RIGHT_TRIGGER + " Check: " + controllerAxis.RIGHT_TRIGGER);
        }

        if(!controllerAxis.VERTICAL_LEFT_STICKER.equals(VERTICAL_LEFT_STICKER)) {
            System.out.println("Current: " + VERTICAL_LEFT_STICKER + " Check: " + controllerAxis.VERTICAL_LEFT_STICKER);
        }

        if(!controllerAxis.VERTICAL_RIGHT_STICKER.equals(VERTICAL_RIGHT_STICKER)) {
            System.out.println("Current: " + VERTICAL_RIGHT_STICKER + " Check: " + controllerAxis.VERTICAL_RIGHT_STICKER);
        }


        if(!controllerAxis.HORIZONTAL_LEFT_STICKER.equals(HORIZONTAL_LEFT_STICKER)) {
            System.out.println("Current: " + HORIZONTAL_LEFT_STICKER + " Check: " + controllerAxis.HORIZONTAL_LEFT_STICKER);
        }

        if(!controllerAxis.HORIZONTAL_RIGHT_STICKER.equals(HORIZONTAL_RIGHT_STICKER)) {
            System.out.println("Current: " + HORIZONTAL_RIGHT_STICKER + " Check: " + controllerAxis.HORIZONTAL_RIGHT_STICKER);
        }

    }
}
