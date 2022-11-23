package HackClient.module;

import HackClient.module.movement.Flight;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private List<Mod> mods = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public List<Mod> getModules() {
        return mods;
    }

    public List<Mod> getEnabeledModules(){
        List<Mod> enabeld = new ArrayList<>();
        for (Mod mod : mods) {
            if (mod.isEnabled()) enabeld.add(mod);
        }

        return enabeld;
    }

    private void addModules() {
        mods.add(new Flight());
    }
}
