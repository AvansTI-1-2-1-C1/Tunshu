package HardwareLayer.Navigation;

import HardwareLayer.HardwareOnAndOff;
import HardwareLayer.Updatable;

public class LineFollower implements Updatable, HardwareOnAndOff {
    private boolean isOn;

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {

    }

    @Override
    public void on() {
        isOn = true;
    }

    @Override
    public void off() {
        isOn = false;
    }
}
