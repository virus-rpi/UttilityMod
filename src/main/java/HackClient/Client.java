package HackClient;

import HackClient.module.API;
import HackClient.module.Mod;
import HackClient.module.ModuleManager;
import HackClient.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

public class Client implements ModInitializer {
    public static final Client INSTANCE = new Client();
    public Logger logger = LogManager.getLogger(Client.class);

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final API api = new API(mc);

    public static API getAPI() {
        return api;
    }

    @Override
    public void onInitialize(){
        logger.info("Startup...");
    }

    public void onKeyPress(int key, int action) {
        if (mc.currentScreen == null) {
            if (action == GLFW.GLFW_PRESS) {
                for (Mod module : ModuleManager.INSTANCE.getModules()) {
                    if (key == module.getKey()) module.toggle();
                }
                if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                    mc.setScreen(ClickGUI.INSTANCE);
                }
            }
        }
    }

    public void onTick(){
        if (mc.player != null) {
            for (Mod module : ModuleManager.INSTANCE.getEnabledModules()){
                module.onTick();
            }
        }
        if (api.getDisconnect()) {
            try {
                mc.disconnect(new MultiplayerScreen(new TitleScreen()));
            } catch (Exception ignored) {
            }
            api.resetDisconnect();
        }
        if (api.getMultiplayerScreen()) {
            try {
                mc.setScreen(new MultiplayerScreen(new TitleScreen()));
            } catch (Exception ignored) {
            }
            api.resetMultiplayerScreen();
        }
    }
}
