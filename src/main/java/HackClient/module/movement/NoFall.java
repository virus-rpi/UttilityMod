package HackClient.module.movement;

import HackClient.module.Mod;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Mod{
    public NoFall() {
        super("NoFall", "Don't get fall damage", Mod.Category.MOVEMENT, "NoFall");
        this.setKey(GLFW.GLFW_KEY_N);
    }

    @Override
    public void onTick(){
        if(mc.player.fallDistance <= (mc.player.isFallFlying() ? 1 : 2))
            return;

        if(mc.player.isFallFlying() && mc.player.isSneaking()
                && !isFallingFastEnoughToCauseDamage(mc.player))
            return;

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
    }

    private boolean isFallingFastEnoughToCauseDamage(ClientPlayerEntity player)
    {
        assert mc.player != null;
        return mc.player.getVelocity().y < -0.5;
    }
}
