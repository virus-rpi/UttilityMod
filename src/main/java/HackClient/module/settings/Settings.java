package HackClient.module.settings;

public class Settings {
    private String name;
    private boolean visible = true;

    public Settings(String name){
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }
}
