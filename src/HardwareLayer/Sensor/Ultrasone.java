package HardwareLayer.Sensor;

import HardwareLayer.HardwareOnAndOff;
import HeadInterfaces.Updatable;

public class Ultrasone implements Updatable, HardwareOnAndOff {
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
