package HackClient.module.movement;

import HackClient.Client;
import HackClient.mixins.ClientConnectionInvoker;
import HackClient.module.Mod;
import HackClient.module.settings.BooleanSetting;
import HackClient.module.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class Flight extends Mod {
    private int time = 0;
    public NumberSetting speed = new NumberSetting("Speed", 0, 100, 1, 1);
    public NumberSetting yVel = new NumberSetting("Y-Velocity", 0, 100, 7, 1);
    public NumberSetting tth = new NumberSetting("Tick Threshold", 1, 80, 20, 1);
    public BooleanSetting fixPos = new BooleanSetting("Fix Position", false);
    public BooleanSetting antikick = new BooleanSetting("AntiKick", true);
    public NumberSetting antikickMultiplier = new NumberSetting("AntiKick amount", 1, 5, 1, 1);
    public Logger logger = LogManager.getLogger(Client.class);
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT, "Flight");
        this.setKey(GLFW.GLFW_KEY_V);
        addSettings(speed, tth, antikick, yVel, fixPos, antikickMultiplier);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed(speed.getValueFloat()/10);
        if (antikick.isEnabled()) doAntiKick();
        super.onTick();
    }
    @Override
    public void onDisable(){
        assert mc.player != null;
        mc.player.getAbilities().flying = false;
        super.onDisable();
    }

    public static class PacketHelper {
        public static void sendPosition(Vec3d pos) {
            MinecraftClient client = MinecraftClient.getInstance();
            assert client.player != null;
            ClientConnectionInvoker conn = (ClientConnectionInvoker)client.player.networkHandler.getConnection();
            Packet<ServerPlayPacketListener> packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
            conn.sendIm(packet, null);
            }
        }

    private void doAntiKick()
    {
        time++;

        if (time > tth.geValueInt()){
            double amount = antikickMultiplier.getValueFloat() * 0.0433D;
            mc.options.sneakKey.setPressed(false);
            mc.options.jumpKey.setPressed(false);
            assert mc.player != null;
            PacketHelper.sendPosition(mc.player.getPos().subtract(0.0, amount, 0.0));
            Vec3d velocity = mc.player.getVelocity();
            mc.player.setVelocity(velocity.x, -(yVel.getValueFloat()/100), velocity.z);
            time = 0;
            if (fixPos.isEnabled()) {
                PacketHelper.sendPosition(mc.player.getPos().add(0.0, amount, 0.0));
                velocity = mc.player.getVelocity();
                mc.player.setVelocity(velocity.x, -(yVel.getValueFloat()/100), velocity.z);
            }
        }
        logger.info(time);
    }

}
