package com.github.fernthedev.controllerremapmod.core.joystick;

import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

@Data
@Getter
//@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerButtons {


    private Mapping mapping;

    private ControllerButtons(@NonNull Mapping mapping) {
        this.mapping = mapping;
    }

    private ControllerButtonState A;
    private ControllerButtonState B;
    private ControllerButtonState X;
    private ControllerButtonState Y;

    private ControllerButtonState BUMPER_LEFT;
    private ControllerButtonState BUMPER_RIGHT;

    private ControllerButtonState EXTRA_BUTTON;
    private ControllerButtonState START_BUTTON;

    private ControllerButtonState LEFT_STICKER;
    private ControllerButtonState RIGHT_STICKER;

    private ControllerButtonState DPAD_UP;
    private ControllerButtonState DPAD_RIGHT;
    private ControllerButtonState DPAD_DOWN;
    private ControllerButtonState DPAD_LEFT;

    private void build() {
        A = new ControllerButtonState(mapping.getButtonMapping().getA());
        B = new ControllerButtonState(mapping.getButtonMapping().getB());
        X = new ControllerButtonState(mapping.getButtonMapping().getX());
        Y = new ControllerButtonState(mapping.getButtonMapping().getY());

        BUMPER_LEFT = new ControllerButtonState(mapping.getButtonMapping().getBUMPER_LEFT());
        BUMPER_RIGHT = new ControllerButtonState(mapping.getButtonMapping().getBUMPER_RIGHT());

        EXTRA_BUTTON = new ControllerButtonState(mapping.getButtonMapping().getEXTRA_BUTTON());
        START_BUTTON = new ControllerButtonState(mapping.getButtonMapping().getSTART_BUTTON());

        LEFT_STICKER = new ControllerButtonState(mapping.getButtonMapping().getLEFT_STICKER());
        RIGHT_STICKER = new ControllerButtonState(mapping.getButtonMapping().getRIGHT_STICKER());

        DPAD_UP = new ControllerButtonState(mapping.getButtonMapping().getDPAD_UP());
        DPAD_RIGHT = new ControllerButtonState(mapping.getButtonMapping().getDPAD_RIGHT());
        DPAD_DOWN = new ControllerButtonState(mapping.getButtonMapping().getDPAD_DOWN());
        DPAD_LEFT = new ControllerButtonState(mapping.getButtonMapping().getDPAD_LEFT());

    }

    public static ControllerButtons getControllerButtons(int controllerIndex, @NonNull Mapping mapping) {
        Validate.notNull(mapping);

        ControllerButtons controllerButtons = new ControllerButtons(mapping);
        controllerButtons.build();

        ByteBuffer buttons = glfwGetJoystickButtons(controllerIndex);

        int buttonID = 1;
        assert buttons != null;
        while (buttons.hasRemaining()) {
            int state = buttons.get();
            boolean pressed = state == GLFW_PRESS;

            int id = buttonID - 1;

            if(id == controllerButtons.A.getButtonIndex()) controllerButtons.A.setState(pressed);
            if(id == controllerButtons.B.getButtonIndex()) controllerButtons.B.setState(pressed);
            if(id == controllerButtons.X.getButtonIndex()) controllerButtons.X.setState(pressed);
            if(id == controllerButtons.Y.getButtonIndex()) controllerButtons.Y.setState(pressed);

            if(id == controllerButtons.BUMPER_LEFT.getButtonIndex()) controllerButtons.BUMPER_LEFT.setState(pressed);
            if(id == controllerButtons.BUMPER_RIGHT.getButtonIndex()) controllerButtons.BUMPER_RIGHT.setState(pressed);

            if(id == controllerButtons.EXTRA_BUTTON.getButtonIndex()) controllerButtons.EXTRA_BUTTON.setState(pressed);
            if(id == controllerButtons.START_BUTTON.getButtonIndex()) controllerButtons.START_BUTTON.setState(pressed);
            if(id == controllerButtons.LEFT_STICKER.getButtonIndex()) controllerButtons.LEFT_STICKER.setState(pressed);
            if(id == controllerButtons.RIGHT_STICKER.getButtonIndex()) controllerButtons.RIGHT_STICKER.setState(pressed);

            if(id == controllerButtons.DPAD_UP.getButtonIndex()) controllerButtons.DPAD_UP.setState(pressed);
            if(id == controllerButtons.DPAD_DOWN.getButtonIndex()) controllerButtons.DPAD_DOWN.setState(pressed);
            if(id == controllerButtons.DPAD_LEFT.getButtonIndex()) controllerButtons.DPAD_LEFT.setState(pressed);
            if(id == controllerButtons.DPAD_RIGHT.getButtonIndex()) controllerButtons.DPAD_RIGHT.setState(pressed);

            if (pressed) {
                ControllerHandler.getLogger().info(id + " was pressed");
            }

            buttonID++;
        }
        return controllerButtons;
    }

}
