package com.github.fernthedev.controllerremapmod.core;

import com.github.fernthedev.controllerremapmod.config.IConfigHandler;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

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

    String getModID();

    Path getConfigDir();

    IConfigHandler getConfigHandler();

    Object getModContainer();
}
