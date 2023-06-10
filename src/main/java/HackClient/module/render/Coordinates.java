package HackClient.module.render;

import HackClient.module.Mod;
import HackClient.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.List;

public class Coordinates extends Mod {
    private MatrixStack matrices;
    public double x = 0.0F;
    public double y = 0.0F;
    public double z = 0.0F;
    private static MinecraftClient mc = MinecraftClient.getInstance();

    private static void renderArrayList(DrawContext context) {
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();
        int sHight = mc.getWindow().getScaledHeight();
        List<Mod> enabled = ModuleManager.INSTANCE.getEnabeledModules();
        enabled.sort(Comparator.comparing(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        for (Mod mod : enabled) {
            context.drawTextWithShadow(mc.textRenderer, mod.getDisplayName(), (sWidth-4)-mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
            index++;
        }
    }
    public Coordinates() {
        super("Coordinates", "Displays coordinates", Category.RENDER, "Coordinates");
    }

    @Override
    public void onTick() {
        super.onTick();
        x = mc.player.getX();
        y = mc.player.getY();
        z = mc.player.getZ();
    }
}
