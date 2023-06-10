package HackClient.ui.screens.clickgui;

import HackClient.Client;
import HackClient.module.Mod;
import HackClient.module.settings.BooleanSetting;
import HackClient.module.settings.ModeSetting;
import HackClient.module.settings.NumberSetting;
import HackClient.module.settings.Settings;
import HackClient.ui.screens.clickgui.setting.CheckBox;
import HackClient.ui.screens.clickgui.setting.Component;
import HackClient.ui.screens.clickgui.setting.ModeBox;
import HackClient.ui.screens.clickgui.setting.Slider;
import net.minecraft.client.gui.DrawContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    public Mod module;
    public Frame parent;
    public int offset;
    public List<Component> components;
    public boolean extended;

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.components = new ArrayList<>();
        this.extended = false;

        int setOffset = parent.height;
        for (Settings setting : module.getSettings()){
            if (setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, this, setOffset));
            } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, this, setOffset));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, this, setOffset));
            }
            setOffset += parent.height;

        }
    }

    public void render(DrawContext context, double mouseX, double mouseY, float delta) {
        context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0xa0000000, true).getRGB());
        if (isHovered(mouseX, mouseY)) context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0xa0000000, true).getRGB());

        int offset_center = ((parent.height/2)-parent.mc.textRenderer.fontHeight / 2);
        context.drawTextWithShadow(parent.mc.textRenderer, module.getName(), parent.x+offset_center, parent.y + offset+offset_center, module.isEnabled() ? Color.RED.getRGB() : -1);

        if (extended) {
            for (Component component : components) {
                component.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1){
                extended = !extended;
                parent.updateButtons();
            }
        }
        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button){
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY){
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }
}
