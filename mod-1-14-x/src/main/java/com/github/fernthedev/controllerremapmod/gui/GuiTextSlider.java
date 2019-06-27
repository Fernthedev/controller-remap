package com.github.fernthedev.controllerremapmod.gui;

import net.minecraftforge.fml.client.config.GuiSlider;

import javax.annotation.Nullable;

public class GuiTextSlider extends GuiSlider {

    public GuiTextSlider(int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, IPressable handler) {
        super(xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, handler);
    }

    public GuiTextSlider(int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, IPressable handler, @Nullable ISlider par) {
        super(xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, handler, par);
    }

    public GuiTextSlider(int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, IPressable handler, ISlider par) {
        super(xPos, yPos, displayStr, minVal, maxVal, currentVal, handler, par);
    }

    @Override
    public void updateSlider() {
        super.updateSlider();
        setMessage(dispString);
    }
}
