package HackClient.ui.screens.clickgui.setting;

import HackClient.module.settings.BooleanSetting;
import HackClient.module.settings.Settings;
import HackClient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class CheckBox extends Component{

    private BooleanSetting boolSet = (BooleanSetting)setting;

    public CheckBox(Settings setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.boolSet = (BooleanSetting)setting;
    }

    @Override
    public void render(MatrixStack matrices, double mouseX, double mouseY, float delta) {
        DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.offset + parent.parent.height, new Color(0x78000000, true).getRGB());
        int offset_center = ((parent.parent.height/2)-mc.textRenderer.fontHeight / 2);
        mc.textRenderer.drawWithShadow(matrices, (boolSet.getName() + ": " ), parent.parent.x + offset_center, parent.parent.y + offset_center + parent.offset + offset, -1);
        mc.textRenderer.drawWithShadow(matrices, (boolSet.isEnabled() ? "☑" : "☐"), parent.parent.x + parent.parent.width - offset_center - 2 - mc.textRenderer.getWidth("☐"), parent.parent.y + offset_center + parent.offset + offset, -1);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            boolSet.toggle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

}
