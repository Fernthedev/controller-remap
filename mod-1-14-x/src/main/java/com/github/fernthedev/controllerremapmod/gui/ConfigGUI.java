package com.github.fernthedev.controllerremapmod.gui;

import com.github.fernthedev.controllerremapmod.config.ISettingsConfig;
import com.github.fernthedev.controllerremapmod.config.MappingConfig;
import com.github.fernthedev.controllerremapmod.config.ui.IConfigGUI;
import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.util.ArrayList;
import java.util.List;

public class ConfigGUI extends Screen implements IConfigGUI {

    private GuiSlider sensitivity;
    private GuiTextSlider mappingListSlider;
    private GuiSlider deadzoneLeft;
    private GuiSlider deadzoneRight;

    private static final String mappingFormat = "Mapping (Controller Layout) : %mapping%";

    public ConfigGUI(List<MappingConfig> mappingList) {
        super(new TranslationTextComponent("controllerremapper.gui.config"));
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
    protected void init() {
        // Load settings
        ISettingsConfig settings = ControllerHandler.getConfigHandler().getSettings();
        settings.sync();

        int scaledHeight = height / 4 + 48;

        // Sensitivity bar
        sensitivity = new GuiSlider(width / 2 - 100, scaledHeight + 72 + 20, "Sensitivity", 0.01, 5, settings.getSensitivity(),null, slider -> {
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



            mappingListSlider = new GuiTextSlider(sliderX, sliderY, formattedMapping(), 0, mappingList.size() - 1, curIndex, null, slider -> {
//                ControllerHandler.getHandler().getLogger().info("The current mappings are " +  mappingList);

                slider.maxValue = mappingList.size() - 1;

                if(!mappingList.isEmpty()) {
                    settings.setSelectedMapping(mappingList.get(slider.getValueInt()));
                    slider.dispString = formattedMapping();
                    settings.sync();
                }


            });
        } else {
            mappingListSlider = new GuiTextSlider(sliderX, sliderY, formattedMapping(), 0, 0, 0,null, null);
            mappingListSlider.active = false;
        }

        mappingListSlider.setWidth((int) (mappingListSlider.getWidth() * 1.5));
        mappingListSlider.setMessage(formattedMapping());

        //Deadzone sliders
        deadzoneLeft = new GuiSlider(width / 2 - 100, scaledHeight + 32 + 20, "Deadzone Left Stick: ", 0.01, 1, settings.getDeadzoneLeft(), null, slider -> {
            settings.setDeadzoneLeft(deadzoneLeft.getValue());
            settings.sync();
        });

        deadzoneRight = new GuiSlider(width / 2 - 100, scaledHeight + 12 + 20, "Deadzone Right Stick: ", 0.01, 1,  settings.getDeadzoneRight(), null, slider -> {
            settings.setDeadzoneRight(deadzoneRight.getValue());
            settings.sync();
        });

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


        super.init();
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
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
    }

}
