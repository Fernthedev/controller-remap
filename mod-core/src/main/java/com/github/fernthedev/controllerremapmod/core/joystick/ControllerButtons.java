package com.github.fernthedev.controllerremapmod.core.joystick;

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
    
    private void remap() {
        A.setButtonIndex(mapping.getButtonMapping().getA());
        B.setButtonIndex(mapping.getButtonMapping().getB());
        X.setButtonIndex(mapping.getButtonMapping().getX());
        Y.setButtonIndex(mapping.getButtonMapping().getY());

        BUMPER_LEFT.setButtonIndex(mapping.getButtonMapping().getBUMPER_LEFT());
        BUMPER_RIGHT.setButtonIndex(mapping.getButtonMapping().getBUMPER_RIGHT());

        EXTRA_BUTTON.setButtonIndex(mapping.getButtonMapping().getEXTRA_BUTTON());
        START_BUTTON.setButtonIndex(mapping.getButtonMapping().getSTART_BUTTON());

        LEFT_STICKER.setButtonIndex(mapping.getButtonMapping().getLEFT_STICKER());
        RIGHT_STICKER.setButtonIndex(mapping.getButtonMapping().getRIGHT_STICKER());

        DPAD_UP.setButtonIndex(mapping.getButtonMapping().getDPAD_UP());
        DPAD_RIGHT.setButtonIndex(mapping.getButtonMapping().getDPAD_RIGHT());
        DPAD_DOWN.setButtonIndex(mapping.getButtonMapping().getDPAD_DOWN());
        DPAD_LEFT.setButtonIndex(mapping.getButtonMapping().getDPAD_LEFT());
    }
    
    public static ControllerButtons buildControllerButtons(int controllerIndex, @NonNull Mapping mapping) {
        ControllerButtons controllerButtons = new ControllerButtons(mapping);

        controllerButtons.build();
        
        return controllerButtons.getControllerButtons(controllerIndex, mapping);
    }

    public ControllerButtons getControllerButtons(int controllerIndex, @NonNull Mapping mapping) {
        Validate.notNull(mapping);

        if(this.mapping != mapping) {
            this.mapping = mapping;
            remap();
        }
        

        ByteBuffer buttons = glfwGetJoystickButtons(controllerIndex);

        int buttonID = 1;
        assert buttons != null;
        while (buttons.hasRemaining()) {
            int state = buttons.get();
            boolean pressed = state == GLFW_PRESS;

            int id = buttonID - 1;

            if(id == A.getButtonIndex()) A.setHeld(pressed);
            if(id == B.getButtonIndex()) B.setHeld(pressed);
            if(id == X.getButtonIndex()) X.setHeld(pressed);
            if(id == Y.getButtonIndex()) Y.setHeld(pressed);

            if(id == BUMPER_LEFT.getButtonIndex()) BUMPER_LEFT.setHeld(pressed);
            if(id == BUMPER_RIGHT.getButtonIndex()) BUMPER_RIGHT.setHeld(pressed);

            if(id == EXTRA_BUTTON.getButtonIndex()) EXTRA_BUTTON.setHeld(pressed);
            if(id == START_BUTTON.getButtonIndex()) START_BUTTON.setHeld(pressed);
            if(id == LEFT_STICKER.getButtonIndex()) LEFT_STICKER.setHeld(pressed);
            if(id == RIGHT_STICKER.getButtonIndex()) RIGHT_STICKER.setHeld(pressed);

            if(id == DPAD_UP.getButtonIndex()) DPAD_UP.setHeld(pressed);
            if(id == DPAD_DOWN.getButtonIndex()) DPAD_DOWN.setHeld(pressed);
            if(id == DPAD_LEFT.getButtonIndex()) DPAD_LEFT.setHeld(pressed);
            if(id == DPAD_RIGHT.getButtonIndex()) DPAD_RIGHT.setHeld(pressed);

            buttonID++;
        }
        return this;
    }

}
