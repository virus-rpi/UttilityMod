package HackClient.ui.screens.clickgui.setting;

import HackClient.module.settings.BooleanSetting;
import HackClient.module.settings.ModeSetting;
import HackClient.module.settings.Settings;
import HackClient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class ModeBox extends Component{
    private ModeSetting modSet = (ModeSetting)setting;

    public ModeBox(Settings setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.modSet = (ModeSetting)setting;
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta) {
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.offset + parent.parent.height, new Color(0x78000000, true).getRGB());
        int offset_center = ((parent.parent.height/2)-mc.textRenderer.fontHeight / 2);
        context.drawTextWithShadow(mc.textRenderer, (modSet.getName() + ": " ), parent.parent.x + offset_center, parent.parent.y + offset_center + parent.offset + offset, -1);
        context.drawTextWithShadow(mc.textRenderer, modSet.getMode(), parent.parent.x + parent.parent.width - offset_center - 2 - mc.textRenderer.getWidth(modSet.getMode()), parent.parent.y + offset_center + parent.offset + offset, -1);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            modSet.cicle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
