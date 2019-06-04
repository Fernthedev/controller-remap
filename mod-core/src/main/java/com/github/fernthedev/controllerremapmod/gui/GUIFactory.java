package com.github.fernthedev.controllerremapmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

public class GUIFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    /**
     * Return the name of a class extending {@link GuiScreen}. This class will
     * be instantiated when the "config" button is pressed in the mod list. It will
     * have a single argument constructor - the "parent" screen, the same as all
     * Minecraft GUIs. The expected behaviour is that this screen will replace the
     * "mod list" screen completely, and will return to the mod list screen through
     * the parent link, once the appropriate action is taken from the config screen.
     * <p>
     * A null from this method indicates that the mod does not provide a "config"
     * button GUI screen, and the config button will be hidden/disabled.
     * <p>
     * This config GUI is anticipated to provide configuration to the mod in a friendly
     * visual way. It should not be abused to set internals such as IDs (they're gonna
     * keep disappearing anyway), but rather, interesting behaviours. This config GUI
     * is never run when a server game is running, and should be used to configure
     * desired behaviours that affect server state. Costs, mod game modes, stuff like that
     * can be changed here.
     *
     * @return A class that will be instantiated on clicks on the config button
     * or null if no GUI is desired.
     */
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
//        return ControllerHandler.getHandler().getGuiConfigMenu().getClass();
        return null;
    }


    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    /**
     * Return an instance of a {@link RuntimeOptionGuiHandler} that handles painting the
     * right hand side option screen for the specified {@link RuntimeOptionCategoryElement}.
     *
     * @param element The element we wish to paint for
     * @return The Handler for painting it
     */
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
