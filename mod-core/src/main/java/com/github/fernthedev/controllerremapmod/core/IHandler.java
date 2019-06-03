package com.github.fernthedev.controllerremapmod.core;

import org.apache.logging.log4j.Logger;

public interface IHandler {

    Logger getLogger();

    void setControllerHandler(ControllerHandler controllerHandler);

    void closeGUI();

    void renderPlayerList(boolean visible);

    void displayChat();

    void toggle3rdPerson();

    boolean isGuiOpen();

    void clickRightMouse();

    void clickMouse(boolean leftClick,boolean leftClickHeld);

    boolean isInventory();

    void openMainMenu();

    void printChat(String s);
}
