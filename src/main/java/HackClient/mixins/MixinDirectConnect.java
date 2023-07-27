package HackClient.mixins;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import HackClient.Client;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(MultiplayerScreen.class)
public abstract class MixinDirectConnect  {
    @Shadow protected abstract void connect(ServerInfo entry);

    @Unique
    public void connectTo(String address) {
        ServerInfo server = new ServerInfo("Server", address, false);
        connect(server);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        if (!Objects.equals(Client.getAPI().getAddress(), "")) {
            connectTo(Client.getAPI().getAddress());
            Client.getAPI().resetAddress();
        }
    }
}