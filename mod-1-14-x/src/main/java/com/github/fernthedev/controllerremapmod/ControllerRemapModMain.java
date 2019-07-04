package com.github.fernthedev.controllerremapmod;

import com.github.fernthedev.controllerremapmod.config.IConfigHandler;
import com.github.fernthedev.controllerremapmod.config.toml.ConfigHandler;
import com.github.fernthedev.controllerremapmod.core.ControllerHandler;
import com.github.fernthedev.controllerremapmod.core.IHandler;
import com.github.fernthedev.controllerremapmod.gui.ConfigGUI;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.overlay.PlayerTabOverlayGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ControllerRemapModMain.MODID)
public class ControllerRemapModMain implements IHandler {
    // Directly reference a log4j logger.
    private static final Logger logger = LogManager.getLogger();

    private ConfigHandler configHandler;

    static final String MODID = "controller-remap";

    private static Class<? extends Minecraft> minecraftClass;

    private Method clickMethod;
    private Method rightClickMethod;

    @Getter
    private ControllerHandler controllerHandler;
    private Method clickBlockMethod;


    public ControllerRemapModMain() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(new MyEventHandler(this));

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        logger.info("Registering config");

        configHandler = ConfigHandler.registerSpec(() -> {
            controllerHandler.init();
            // Register ourselves for server and other game events we are interested in

        });


        File dir = new File(getConfigDir().toFile(),"mappings");

        ControllerHandler.createMappingTemplates(dir);

        modLoadingContext.registerConfig(ModConfig.Type.CLIENT,ConfigHandler.getCLIENT_SPEC());



        ControllerHandler.setHandler(this);

    }

    /**
     * Pre init
     */
    private void setup(final FMLCommonSetupEvent e) {
        // some preinit code
        minecraftClass = Minecraft.getInstance().getClass();
        controllerHandler.init();

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        logger.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setControllerHandler(ControllerHandler controllerHandler) {
        this.controllerHandler = controllerHandler;
    }

    @Override
    public void closeGUI() {
        if(Minecraft.getInstance().currentScreen != null)
        Minecraft.getInstance().currentScreen.onClose();
    }


    @Override
    public void renderPlayerList(boolean visible) {
        PlayerTabOverlayGui tabOverlay = Minecraft.getInstance().ingameGUI.getTabList();

        if(visible) {
            int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();

            Scoreboard scoreboard = Minecraft.getInstance().world.getScoreboard();
            ScoreObjective scoreobjective = null;
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(Minecraft.getInstance().player.getScoreboardName());
            if (scoreplayerteam != null) {
                int j = scoreplayerteam.getColor().getColorIndex();
                if (j >= 0) {
                    scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j);
                }
            }

            ScoreObjective scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
            if (Minecraft.getInstance().isIntegratedServerRunning() && Minecraft.getInstance().player.connection.getPlayerInfoMap().size() <= 1 && scoreobjective1 == null) {
                tabOverlay.setVisible(false);
            } else {
                tabOverlay.setVisible(true);
                tabOverlay.render(scaledWidth, scoreboard, scoreobjective1);
            }

        }else{
            tabOverlay.setVisible(false);
        }
    }

    @Override
    public void displayChat() {
        Minecraft.getInstance().displayGuiScreen(new ChatScreen(""));
    }

    @Override
    public void toggle3rdPerson() {
        Minecraft.getInstance().gameSettings.thirdPersonView++;
        if (Minecraft.getInstance().gameSettings.thirdPersonView > 2) {
            Minecraft.getInstance().gameSettings.thirdPersonView = 0;
        }

    }

    @Override
    public boolean isGuiOpen() {
        return Minecraft.getInstance().currentScreen != null;
    }

    @Override
    public void clickRightMouse() {
        try {
            if(rightClickMethod == null) {
                rightClickMethod = ObfuscationReflectionHelper.findMethod(minecraftClass,"func_147121_ag");
                rightClickMethod.setAccessible(true);
            }

            rightClickMethod.invoke(Minecraft.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickMouse(boolean leftClick,boolean leftClickHeld) {
        try {

            if(clickMethod == null) {
                clickMethod = ObfuscationReflectionHelper.findMethod(minecraftClass,"func_147116_af");
                clickBlockMethod = ObfuscationReflectionHelper.findMethod(minecraftClass,"func_147115_a",boolean.class);
                clickMethod.setAccessible(true);
                clickBlockMethod.setAccessible(true);
            }

            if(leftClick) {
                clickMethod.invoke(Minecraft.getInstance());
            }

            clickBlockMethod.invoke(Minecraft.getInstance(),leftClickHeld);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isInventory() {
        return Minecraft.getInstance().currentScreen instanceof InventoryScreen;
    }

    @Override
    public void openMainMenu() {
        Minecraft.getInstance().displayGuiScreen(new IngameMenuScreen(true));
    }

    @Override
    public void printChat(String s) {
        Minecraft.getInstance().player.sendChatMessage(s);
    }

    @Override
    public String getModID() {
        return MODID;
    }

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }


    @Override
    public IConfigHandler getConfigHandler() {
        if(configHandler == null) {
            configHandler = new ConfigHandler(this);
        }
        return configHandler;
    }

    @Override
    public Screen getGui() {
        return Minecraft.getInstance().currentScreen;
    }

    @Override
    public void displayOptions() {
        Minecraft.getInstance().displayGuiScreen(new ConfigGUI(configHandler.getSettings().getLoadedMappingList(),Minecraft.getInstance().currentScreen));
    }

    public ModContainer getModContainer() {
        return ModLoadingContext.get().getActiveContainer();
    }
}
