package HackClient.ui.screens.clickgui;

import HackClient.Client;
import HackClient.module.Mod;
import HackClient.module.Mod.Category;
import HackClient.module.ModuleManager;
import HackClient.ui.screens.clickgui.setting.Component;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {

    public  int x, y, width, height, dragX, dragY, r, g, b, rgb_mode;
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
        this.r = 254;
        this.g = 0;
        this.b = 100;
        this.rgb_mode = 0;

        buttons = new ArrayList<>();

        int offset = height;
        for (Mod mod : ModuleManager.INSTANCE.getModulesInCategory(category)) {
            buttons.add(new ModuleButton(mod, this, offset));
            offset += height;
        }
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        DrawableHelper.fill(matrices, x, y, x+width, y + height, rgbColor());
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
        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.mouseReleased(mouseX, mouseY, button);
            }
        }
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

    private int i = 0;
    public int rgbColor(){
        switch (rgb_mode){
            case 0 -> {
                r++;
                g--;
            }
            case 1 -> {
                g++;
                b--;
            }
            case 2 -> {
                b++;
                r--;
            }
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }
        if (i > 220) {
            i = 0;
            rgb_mode++;
            if (rgb_mode > 2) {
                rgb_mode = 0;
            }
        }
        i++;
        return new Color(r, g, b).getRGB();
    }

    public void updateButtons() {
        int offset = height;

        for (ModuleButton button : buttons) {
            button.offset = offset;
            offset += height;
            if (button.extended) {
                for (Component component : button.components) {
                    if (component.setting.isVisible()) offset += height;
                }
            }
        }
    }
}
