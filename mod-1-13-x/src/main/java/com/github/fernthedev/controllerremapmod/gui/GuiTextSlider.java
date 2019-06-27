package com.github.fernthedev.controllerremapmod.gui;

import net.minecraftforge.fml.client.config.GuiSlider;

import javax.annotation.Nullable;

public class GuiTextSlider extends GuiSlider {
    public GuiTextSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr);
    }

    public GuiTextSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, @Nullable ISlider par) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, par);
    }

    public GuiTextSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        super(id, xPos, yPos, displayStr, minVal, maxVal, currentVal, par);
    }

    @Override
    public void updateSlider() {
        super.updateSlider();
        displayString = dispString;
    }
}
