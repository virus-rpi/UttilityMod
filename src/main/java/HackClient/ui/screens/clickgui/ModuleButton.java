package HackClient.ui.screens.clickgui;

import HackClient.module.Mod;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class ModuleButton {
    public Mod module;
    public Frame parent;
    public int offset;

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(MatrixStack matrices, double mouseX, double mouseY, float delta) {
        DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0xa0000000, true).getRGB());
        if (isHovered(mouseX, mouseY)) DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0xa0000000, true).getRGB());

        int offset_center = ((parent.height/2)-parent.mc.textRenderer.fontHeight / 2);
        parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x+offset_center, parent.y + offset+offset_center, module.isEnabled() ? Color.RED.getRGB() : -1);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else {

            }
        }
    }

    public boolean isHovered(double mouseX, double mouseY){
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }
}
