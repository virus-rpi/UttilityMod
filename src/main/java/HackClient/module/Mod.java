package HackClient.module;

import HackClient.Client;
import net.minecraft.client.MinecraftClient;

public class Mod {

    private String name;
    private  String description;
    private String displayName;
    private Category category;
    private int key;
    private boolean enabled;
    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name, String description, Category category, String displayName) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.displayName = displayName;
    }

    public void toggle(){
        this.enabled = !this.enabled;
        if (enabled) onEnable();
        else onDisable();
    }
    public void onEnable(){

    }
    public void onDisable(){

    }
    public void onTick(){

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }
    public Category getCategory() {
        return category;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public enum Category {
        COMBAT,
        MOVEMENT,
        RENDER,
        EXPLOIT,
        WORLD
    }
}
