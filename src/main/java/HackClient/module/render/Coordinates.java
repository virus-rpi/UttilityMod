package HackClient.module.render;

import HackClient.module.Mod;
import HackClient.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.Comparator;
import java.util.List;

public class Coordinates extends Mod {
    public double x = 0.0F;
    public double y = 0.0F;
    public double z = 0.0F;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static void renderArrayList(DrawContext context) {
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();
        List<Mod> enabled = ModuleManager.INSTANCE.getEnabledModules();
        enabled.sort(Comparator.comparing(m -> mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

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
        assert mc.player != null;
        x = mc.player.getX();
        y = mc.player.getY();
        z = mc.player.getZ();
    }
}
