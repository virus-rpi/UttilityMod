package HackClient.module.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Settings{
    private String mode;
    private List<String> modes;
    private int index;

    public ModeSetting(String name, String defaultModes,String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.mode = defaultModes;
        this.index = this.modes.indexOf(defaultModes);
    }

    public String getMode() {
        return mode;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.index = modes.indexOf(mode);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.mode = modes.get(index);
    }

    public void cicle(){
        if (index < modes.size() -1){
            index++;
            mode = modes.get(index);
        }
        else if (index >= modes.size() -1){
            index = 0;
            mode = modes.get(0);
        }
    }

    public boolean isMode(String name){
        return this.mode == name;
    }
}
