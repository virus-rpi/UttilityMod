package HackClient.module.movement;

import HackClient.module.Mod;
import HackClient.module.settings.NumberSetting;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {
    private int time = 0;
    public NumberSetting speed = new NumberSetting("Speed", 0, 10, 1, 1);
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT, "Flight");
        this.setKey(GLFW.GLFW_KEY_V);
        addSetting(speed);
    }

    @Override
    public void onTick() {
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed(speed.getValueFloat()/10);
        doAntiKick();
        super.onTick();
    }
    @Override
    public void onDisable(){
        mc.player.getAbilities().flying = false;
        super.onDisable();
    }

    private void doAntiKick()
    {
        if(time > 78 + 2)
            time = 0;

        switch(time)
        {
            case 0 ->
            {
                if(mc.options.sneakKey.isPressed()
                        && !mc.options.jumpKey.isPressed())
                    time = 3;
                else
                    setMotionY(-0.07);
            }

            case 1 -> setMotionY(0.07);

            case 2 -> setMotionY(0);
        }

        time++;
    }

    private void setMotionY(double motionY)
    {
        mc.options.sneakKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);

        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, motionY, velocity.z);
    }

}
