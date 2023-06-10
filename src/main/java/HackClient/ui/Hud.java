package HackClient.ui;

import HackClient.module.Mod;
import HackClient.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

public class Hud {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(DrawContext context, float tickDelta){
        // mc.textRenderer.drawWithShadow(matrices, "Test", 10, 10, -1);
        renderArrayList(context);
    }

    public static void renderArrayList(DrawContext context) {
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();
        int sHight = mc.getWindow().getScaledHeight();
        List<Mod> enabled = ModuleManager.INSTANCE.getEnabeledModules();
        enabled.sort(Comparator.comparing(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        for (Mod mod : enabled) {
            context.drawTextWithShadow(mc.textRenderer, mod.getDisplayName(), (sWidth-4)-mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
            index++;
            if (mod.getName() == "Coordinates") {
                double x = Math.round(mc.player.getX()*100.0)/100.0;
                double y = Math.round(mc.player.getY()*100.0)/100.0;
                double z = Math.round(mc.player.getZ()*100.0)/100.0;
                context.drawTextWithShadow(mc.textRenderer, "X: " + x, 10, 10, -1);
                context.drawTextWithShadow(mc.textRenderer, "Y: " + y, 10, 22, -1);
                context.drawTextWithShadow(mc.textRenderer, "Z: " + z, 10, 34, -1);
            }
        }

    }
}
