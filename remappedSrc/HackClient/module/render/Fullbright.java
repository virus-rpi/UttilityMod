package HackClient.module.render;

import HackClient.module.Mod;
import net.minecraft.client.option.SimpleOption;

public class Fullbright extends Mod {
    SimpleOption<Double> gammaOption = mc.options.getGamma();
    double oldGammaValue;
    boolean wasGammaChanged;
    public Fullbright() {
        super("Fullbright", "Let you see in the dark", Category.RENDER, "FullBright");
        this.wasGammaChanged = false;
        double oldGammaValue = gammaOption.getValue();
    }

    @Override
    public void onTick() {
        setGamma();
    }

    @Override
    public void onDisable(){
        resetGamma();
    }

    private void setGamma()
    {
        boolean wasGammaChanged = true;
        oldGammaValue = gammaOption.getValue();
        gammaOption.setValue(100.0);
    }

    private void resetGamma()
    {
        boolean wasGammaChanged = false;
        gammaOption.setValue(oldGammaValue);
    }

}
