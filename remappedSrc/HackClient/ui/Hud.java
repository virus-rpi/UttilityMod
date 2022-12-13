package HackClient.ui;

import HackClient.module.Mod;
import HackClient.module.ModuleManager;
import HackClient.module.render.Coordinates;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class Hud {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(MatrixStack matrices, float tickDelta){
        // mc.textRenderer.drawWithShadow(matrices, "Test", 10, 10, -1);
        renderArrayList(matrices);
    }

    public static void renderArrayList(MatrixStack matrices) {
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();
        int sHight = mc.getWindow().getScaledHeight();
        List<Mod> enabled = ModuleManager.INSTANCE.getEnabeledModules();
        enabled.sort(Comparator.comparing(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        for (Mod mod : enabled) {
            mc.textRenderer.drawWithShadow(matrices, mod.getDisplayName(), (sWidth-4)-mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
            index++;
            if (mod.getName() == "Coordinates") {
                double x = Math.round(mc.player.getX()*100.0)/100.0;
                double y = Math.round(mc.player.getY()*100.0)/100.0;
                double z = Math.round(mc.player.getZ()*100.0)/100.0;
                mc.textRenderer.drawWithShadow(matrices, "X: " + x, 10, 10, -1);
                mc.textRenderer.drawWithShadow(matrices, "Y: " + y, 10, 22, -1);
                mc.textRenderer.drawWithShadow(matrices, "Z: " + z, 10, 34, -1);
            }
        }

    }
}
