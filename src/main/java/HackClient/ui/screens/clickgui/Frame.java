package HackClient.ui.screens.clickgui;

import HackClient.module.Mod;
import HackClient.module.Mod.Category;
import HackClient.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    public  int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging, extended;
    private List<ModuleButton> buttons;

    public MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Category category, int x, int y, int width, int hight) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = hight;
        this.dragging = false;
        this.extended = false;

        buttons = new ArrayList<>();

        int offset = height;
        for (Mod mod : ModuleManager.INSTANCE.getModulesInCategory(category)) {
            buttons.add(new ModuleButton(mod, this, offset));
            offset += height;
        }
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, x, y, x+width, y + height, Color.GREEN.getRGB());
        int offset = ((height/2)-mc.textRenderer.fontHeight / 2);

        mc.textRenderer.drawWithShadow(matrices, category.name, x+offset, y+offset, -1);
        mc.textRenderer.drawWithShadow(matrices, extended ? "-" : "+", x + width - offset - 2 - mc.textRenderer.getWidth("+"), y+offset, -1);

        if (extended){
            for (ModuleButton button : buttons) {
                button.render(matrices, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            }
            else if (button == 1) {
                extended = !extended;
            }
        }
        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button){
        if (button == 0 && dragging) dragging = false;
    }

    public boolean isHovered(double mouseX, double mouseY){
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }
}
