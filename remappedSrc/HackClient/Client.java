package HackClient;

import HackClient.module.Mod;
import HackClient.module.ModuleManager;
import HackClient.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

public class Client implements ModInitializer {
    public static final Client INSTANCE = new Client();
    public Logger logger = LogManager.getLogger(Client.class);

    private MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitialize(){
        logger.info("Startup...");
    }

    public void onKeyPress(int key, int action) {
        if (action == GLFW.GLFW_PRESS) {
            for (Mod module : ModuleManager.INSTANCE.getModules()){
                if (key == module.getKey()) module.toggle();
            }
            if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                mc.setScreen(ClickGUI.INSTANCE);
            }
        }
    }

    public void onTick(){
        if (mc.player != null) {
            for (Mod module : ModuleManager.INSTANCE.getEnabeledModules()){
                module.onTick();
            }
        }
    }
}
