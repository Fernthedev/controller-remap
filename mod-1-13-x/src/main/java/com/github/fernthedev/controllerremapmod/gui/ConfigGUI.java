package com.github.fernthedev.controllerremapmod.gui;

import com.github.fernthedev.controllerremapmod.config.ISettingsConfig;
import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.config.ui.IConfigGUI;
import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.util.ArrayList;
import java.util.List;


public class ConfigGUI extends GuiScreen implements IConfigGUI {

    private GuiSlider sensitivity;
    private GuiSlider deadzoneLeft;
    private GuiSlider deadzoneRight;
    private GuiTextSlider mappingListSlider;

    private GuiSlider scrollSpeed;
    private GuiSlider dropSpeed;

    private GuiButtonExt reloadMappings;

    private int id = 0;
    private int getId() {
        int oldId = id;
        id++;
        return oldId;
    }

    private static final String mappingFormat = "Mapping (Controller Layout) : %mapping%";

    public ConfigGUI(List<MappingConfig> mappingList) {
        this.mappingList = new ArrayList<>(mappingList);
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

        // Scroll speed speed
        scrollSpeed = new GuiSlider(getId(),width / 2 - 100, scaledHeight + 112 + 20, "Scroll Speed (Ticks): ", 1, 100, settings.getScrollSpeed(), slider -> {
            settings.setScrollSpeed(scrollSpeed.getValueInt());
            settings.sync();
        });

        // Drop speed slider
        dropSpeed = new GuiSlider(getId(),width / 2 - 100, scaledHeight + 92 + 20, "Drop Speed (Ticks): ", 1, 100, settings.getDropSpeed(), slider -> {
            settings.setDropSpeed(dropSpeed.getValueInt());
            settings.sync();
        });

        // Sensitivity bar
        sensitivity = new GuiSlider(getId(), width / 2 - 100, scaledHeight + 72 + 20, "Sensitivity", 0.01, 5, settings.getSensitivity(), slider -> {
            settings.setSensitivity(sensitivity.getValue());
            settings.sync();
        });



        int sliderX = width / 2 - 100;
        int sliderY = scaledHeight + 52 + 20;
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
        } else {
            mappingListSlider = new GuiTextSlider(1, sliderX, sliderY, formattedMapping(), 0, 0, 0, null);
            mappingListSlider.enabled = false;
        }

        mappingListSlider.width *= 1.5;
        mappingListSlider.displayString = formattedMapping();


        //Deadzone sliders
        deadzoneLeft = new GuiSlider(getId(), width / 2 - 100, scaledHeight + 32 + 20, "Deadzone Left Stick: ", 0.01, 1, settings.getDeadzoneLeft(), slider -> {
            settings.setDeadzoneLeft(deadzoneLeft.getValue());
            settings.sync();
        });

        deadzoneRight = new GuiSlider(getId(), width / 2 - 100, scaledHeight + 12 + 20, "Deadzone Right Stick: ", 0.01, 1, settings.getDeadzoneRight(), slider -> {
            settings.setDeadzoneRight(deadzoneRight.getValue());
            settings.sync();
        });

        reloadMappings = new GuiButtonExt(getId(), width / 2 - 100, scaledHeight + (- 28) + 20, "Reload Mapping") {
            /**
             * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
             */
            @Override
            public void onClick(double mouseX, double mouseY) {
                settings.sync();
                super.onClick(mouseX,mouseY);
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




}
