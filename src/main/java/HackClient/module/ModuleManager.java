package HackClient.module;

import HackClient.module.movement.Flight;
import HackClient.module.movement.NoFall;
import HackClient.module.Mod.Category;
import HackClient.module.render.Coordinates;
import HackClient.module.render.Fullbright;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Mod> mods = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public List<Mod> getModules() {
        return mods;
    }

    public List<Mod> getEnabledModules(){
        List<Mod> enabled = new ArrayList<>();
        for (Mod mod : mods) {
            if (mod.isEnabled()) enabled.add(mod);
        }

        return enabled;
    }

    public List<Mod> getModulesInCategory(Category category){
        List<Mod> categoryModules = new ArrayList<>();

        for (Mod mod : mods){
            if (mod.getCategory() == category) {
                categoryModules.add(mod);
            }
        }
        return categoryModules;
    }

    private void addModules() {
        mods.add(new Flight());
        mods.add(new NoFall());
        mods.add(new Coordinates());
        mods.add(new Fullbright());
    }
}
