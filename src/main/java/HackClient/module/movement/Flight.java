package HackClient.module.movement;

import HackClient.module.Mod;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {
    private int time = 0;
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT, "Flight");
        this.setKey(GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        mc.player.getAbilities().flying = true;
        if (time > 75) {
            mc.player.getAbilities().flying = false;
            time = 0;
        }
        else {
            time++;
        }
        super.onTick();
    }
    @Override
    public void onDisable(){
        mc.player.getAbilities().flying = false;
        super.onDisable();
    }
}
