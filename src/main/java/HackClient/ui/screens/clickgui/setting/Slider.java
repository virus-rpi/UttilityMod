package HackClient.ui.screens.clickgui.setting;

import HackClient.Client;
import HackClient.module.settings.NumberSetting;
import HackClient.module.settings.Settings;
import HackClient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.math.BigDecimal;

public class Slider extends Component{
    public NumberSetting numSet = (NumberSetting)setting;
    private boolean sliding = false;

    public Slider(Settings setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numSet = (NumberSetting)setting;
    }

    @Override
    public void render(DrawContext context, double mouseX, double mouseY, float delta) {
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.offset + parent.parent.height, new Color(0x78000000, true).getRGB());

        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int)(((parent.parent.width * ((numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin())))));

        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + renderWidth, parent.parent.y + offset + parent.offset + parent.parent.height, Color.RED.getRGB());

        if (sliding) {
            if (diff == 0) {
                numSet.setValue(numSet.getMin());
            } else {
                numSet.setValue((((diff / parent.parent.width)*(numSet.getMax()-numSet.getMin())+numSet.getMin())*numSet.getIncrement())*numSet.getIncrement());
            }
        }

        int offset_center = ((parent.parent.height/2)-mc.textRenderer.fontHeight / 2);
        context.drawTextWithShadow(mc.textRenderer, (numSet.getName() + ": " ), parent.parent.x + offset_center, parent.parent.y + offset_center + parent.offset + offset, -1);
        context.drawTextWithShadow(mc.textRenderer, ""+numSet.getValue(), parent.parent.x + parent.parent.width - offset_center - 2 - mc.textRenderer.getWidth(""+rundToPlace(numSet.getValue(), 1)), parent.parent.y + offset_center + parent.offset + offset, -1);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            sliding = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
        super.mouseReleased(mouseX, mouseY, button);
    }

    private double rundToPlace(double value, int place){
        if (place < 0) {
            return value;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
