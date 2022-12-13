package HackClient.module.movement;

import HackClient.Client;
import HackClient.mixins.ClientConnectionInvoker;
import HackClient.module.Mod;
import HackClient.module.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {
    private int time = 0;
    public NumberSetting speed = new NumberSetting("Speed", 0, 10, 1, 1);
    public NumberSetting yVel = new NumberSetting("Y-Velocity", 0, 100, 7, 1);
    public NumberSetting tth = new NumberSetting("Tick Threshold", 1, 80, 20, 1);
    public Logger logger = LogManager.getLogger(Client.class);
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT, "Flight");
        this.setKey(GLFW.GLFW_KEY_V);
        addSettings(speed, yVel, tth);
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

    public class PacketHelper {
        public static void sendPosition(Vec3d pos) {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
            // pos = PacketHelper.fixCoords(pos);
            Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
            conn.sendIm(packet, null);
            }
        }

    private void doAntiKick()
    {
        time++;

        if (time > tth.geValueInt()){ // && mc.player.world.getBlockState(new BlockPos(mc.player.getPos().subtract(0, 0.0433D,0))).isAir()
            mc.options.sneakKey.setPressed(false);
            mc.options.jumpKey.setPressed(false);
            PacketHelper.sendPosition(mc.player.getPos().subtract(0.0, 0.0433D, 0.0));
            Vec3d velocity = mc.player.getVelocity();
            mc.player.setVelocity(velocity.x, -(yVel.getValueFloat()/100), velocity.z);
            time = 0;
        }
        logger.info(time);
        logger.info(mc.player.getPos().subtract(0.0, 0.0533D, 0.0));
        logger.info(mc.player.getPos());
    }

}
