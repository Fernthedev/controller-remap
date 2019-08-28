package com.github.fernthedev.controllerremapmod.gui;

import com.github.fernthedev.controllerremapmod.config.ISettingsConfig;
import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.config.ui.IConfigGUI;
import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class ConfigGUI extends GuiScreen implements IConfigGUI {

    private GuiSlider sensitivity;
    private GuiSlider deadzoneLeft;
    private GuiSlider deadzoneRight;
    private GuiTextSlider mappingListSlider;

    private GuiSlider attackTimerTicks;
    private GuiSlider scrollSpeed;
    private GuiSlider dropSpeed;

    private GuiButtonExt reloadMappings;
    private GuiButtonExt doneButton;

    private GuiScreen parent;

    private int id = 0;


    private int getId() {
        int oldId = id;
        id++;
        return oldId;
    }

    private int maxY;

    private int getButtonY() {
        maxY += 24;
        return maxY;
    }

    private static final String mappingFormat = "Mapping (Controller Layout) : %mapping%";

    public ConfigGUI(List<MappingConfig> mappingList, @Nullable GuiScreen parent) {
        this.mappingList = new ArrayList<>(mappingList);
        this.parent = parent;
        mc = Minecraft.getInstance();
    }

    private String formattedMapping() {
        return mappingFormat.replaceAll("%mapping%",ControllerHandler.getConfigHandler().getSettings().getSelectedMapping().getMapping().getName());
    }


    private List<MappingConfig> mappingList;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    protected void initGui() {
        // Load settings
        ISettingsConfig settings = ControllerHandler.getConfigHandler().getSettings();
        settings.sync();

        int scaledHeight = height / 4 + 48;
        maxY = scaledHeight + (- 28);


        // Attack timer ticks
        attackTimerTicks = new GuiSlider(getId(), width / 2 - 100, getButtonY(), "Attack Timer (Ticks): ", 5, 40, settings.getAttackTimerTicks(), handler -> {
            settings.setAttackTimerTicks(attackTimerTicks.getValueInt());
            settings.sync();
        });

        attackTimerTicks.showDecimal = false;

        // Scroll speed speed
        scrollSpeed = new GuiSlider(getId(),width / 2 - 100, getButtonY(), "Scroll Speed (Ticks): ", 1, 100, settings.getScrollSpeed(), slider -> {
            settings.setScrollSpeed(slider.getValueInt());
            settings.sync();
        });

        scrollSpeed.showDecimal = false;

        // Drop speed slider
        dropSpeed = new GuiSlider(getId(),width / 2 - 100, getButtonY(), "Drop Speed (Ticks): ", 1, 100, settings.getDropSpeed(), slider -> {
            settings.setDropSpeed(slider.getValueInt());
            settings.sync();
        });

        dropSpeed.showDecimal = false;

        // Sensitivity bar
        sensitivity = new GuiSlider(getId(), width / 2 - 100, getButtonY(), "Sensitivity", 0.01, 5, settings.getSensitivity(), slider -> {
            settings.setSensitivity(slider.getValue());
            settings.sync();
        });



        int sliderX = width / 2 - 100;
        int sliderY = getButtonY();


        // Mapping picker
        if(!mappingList.isEmpty()) {
            int curIndex = settings.getLoadedMappingList().indexOf(
                    settings.getSelectedMapping()
            );



            mappingListSlider = new GuiTextSlider(1, sliderX, sliderY, formattedMapping(), 0, mappingList.size() - 1, curIndex, slider -> {
//                ControllerHandler.getHandler().getLogger().info("The current mappings are " +  mappingList);

                slider.maxValue = mappingList.size() - 1;

                if(!mappingList.isEmpty()) {
                    settings.setSelectedMapping(mappingList.get(slider.getValueInt()));
                    slider.dispString = formattedMapping();
                    settings.sync();
                }


            });

            mappingListSlider.setValue(curIndex);
        } else {
            mappingListSlider = new GuiTextSlider(1, sliderX, sliderY, formattedMapping(), 0, 0, 0, null);
            mappingListSlider.enabled = false;
        }

        mappingListSlider.width *= 1.5;
        mappingListSlider.displayString = formattedMapping();


        //Deadzone sliders
        deadzoneLeft = new GuiSlider(getId(), width / 2 - 100, getButtonY(), "Deadzone Left Stick: ", 0.01, 1, settings.getDeadzoneLeft(), slider -> {
            settings.setDeadzoneLeft(slider.getValue());
            settings.sync();
        });

        deadzoneRight = new GuiSlider(getId(), width / 2 - 100, getButtonY(), "Deadzone Right Stick: ", 0.01, 1, settings.getDeadzoneRight(), slider -> {
            settings.setDeadzoneRight(slider.getValue());
            settings.sync();
        });

        reloadMappings = new GuiButtonExt(getId(), width / 2 - 100, getButtonY(), "Reload Mapping") {
            /**
             * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
             */
            @Override
            public void onClick(double mouseX, double mouseY) {
                settings.sync();
                super.onClick(mouseX,mouseY);
            }
        };

        // Exit menu/Done button
        doneButton = new GuiButtonExt(getId(), width / 2 - 100, getButtonY() + 24, 150, 20, "Done") {
            /**
             * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
             *
             * @param mouseX MouseX
             * @param mouseY MouseY
             */
            @Override
            public void onClick(double mouseX, double mouseY) {
                close();
            }
        };

//        if(mapFiles != null) {
//            List<Mapping> mappingList = new ArrayList<>();
//
//            int curIndex = 0;
//
//            for (File file : mapFiles) {
//                Mapping mapping = Mapping.loadFromFile(file);
//                mappingList.add(mapping);
//                if(Objects.equals(mapping.getName(), ControllerHandler.getConfigHandler().getSettings().getSelectedMapping().getName())) {
//                    curIndex = mappingList.indexOf(mapping);
//                }
//            }
//
//            List<Mapping> finalMappingList = mappingList;
//            mappingListSlider = new GuiSlider(1, sliderX, sliderY, formattedMapping(), 0, mapFiles.length - 1, curIndex, slider -> ControllerHandler.getConfigHandler().getSettings().setSelectedMapping(finalMappingList.get(slider.getValueInt())));
//
//
//        }else{
//            mappingListSlider = new GuiSlider(1, sliderX, sliderY, formattedMapping(), 0 ,0, 0, null);
//            mappingListSlider.enabled = false;
//        }

        addButton(attackTimerTicks);
        addButton(doneButton);
        addButton(sensitivity);
        addButton(mappingListSlider);
        addButton(deadzoneLeft);
        addButton(deadzoneRight);
        addButton(scrollSpeed);
        addButton(dropSpeed);
        addButton(reloadMappings);


        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     *
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void close() {
        this.mc.displayGuiScreen(parent);
    }
}
