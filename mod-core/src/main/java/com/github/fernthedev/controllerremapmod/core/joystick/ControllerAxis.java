package com.github.fernthedev.controllerremapmod.core.joystick;

import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import lombok.Data;
import lombok.NonNull;

import java.nio.FloatBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;

@Data
//@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerAxis {

    @NonNull
    private Mapping mapping;

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    private ControllerAxisState HORIZONTAL_LEFT_STICKER = new ControllerAxisState(mapping.getAxesMapping().getHORIZONTAL_LEFT_STICKER());

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    private ControllerAxisState VERTICAL_LEFT_STICKER = new ControllerAxisState(mapping.getAxesMapping().getVERTICAL_LEFT_STICKER());

    /**
     * 1 IS RIGHT
     * -1 IS LEFT
     * 0 IS STATELESS
     */
    private ControllerAxisState VERTICAL_RIGHT_STICKER = new ControllerAxisState(mapping.getAxesMapping().getVERTICAL_RIGHT_STICKER());

    /**
     * -1 IS DOWN
     * 1 IS UP
     * 0 IS STATELESS
     */
    private ControllerAxisState HORIZONTAL_RIGHT_STICKER = new ControllerAxisState(mapping.getAxesMapping().getHORIZONTAL_RIGHT_STICKER());

    /**
     * -1 is default
     * 1 is trigger
     */
    private ControllerAxisState LEFT_TRIGGER = new ControllerAxisState(mapping.getAxesMapping().getLEFT_TRIGGER());

    /**
     * 0 is default
     * 1 is trigger
     */
    private ControllerAxisState RIGHT_TRIGGER = new ControllerAxisState(mapping.getAxesMapping().getRIGHT_TRIGGER());


    /**
     * Gets an instance of {@link ControllerAxis} for the controller specified
     * @param controllerIndex The controller
     * @param mapping
     * @return The instance of {@link ControllerAxis}
     */
    public static ControllerAxis getAxis(int controllerIndex, @NonNull Mapping mapping) {
        ControllerAxis controllerAxis = new ControllerAxis(mapping);

        FloatBuffer axes = glfwGetJoystickAxes(controllerIndex);

        int axisID = 1;
        while (Objects.requireNonNull(axes).hasRemaining()) {
            float state = axes.get();

            int id = axisID - 1;

            if(id == controllerAxis.HORIZONTAL_LEFT_STICKER.getButtonIndex()) controllerAxis.HORIZONTAL_LEFT_STICKER.setValue(state);
            if(id == controllerAxis.VERTICAL_LEFT_STICKER.getButtonIndex()) controllerAxis.VERTICAL_LEFT_STICKER.setValue(state);
            if(id == controllerAxis.HORIZONTAL_RIGHT_STICKER.getButtonIndex()) controllerAxis.HORIZONTAL_RIGHT_STICKER.setValue(state);
            if(id == controllerAxis.LEFT_TRIGGER.getButtonIndex()) controllerAxis.LEFT_TRIGGER.setValue(state);
            if(id == controllerAxis.RIGHT_TRIGGER.getButtonIndex()) controllerAxis.RIGHT_TRIGGER.setValue(state);

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
