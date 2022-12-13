package HackClient.ui.screens.clickgui.setting;

import HackClient.module.settings.Settings;
import HackClient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Component {
    public static Settings setting;
    public ModuleButton parent;
    public int offset;
    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Component(Settings setting, ModuleButton parent, int offset) {
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;

    }

    public void render(MatrixStack matrices, double mouseX, double mouseY, float delta){

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    public void mouseReleased(double mouseX, double mouseY, int button) {

    }


    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.parent.x && mouseX < parent.parent.x+ parent.parent.width && mouseY > parent.parent.y +parent.offset + offset && mouseY < parent.parent.y + parent.offset + offset + parent.parent.height;
    }

    public void keyPressed(int key) {
    }
}
