package HardwareLayer.Sensor;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;

public class Antenna implements Updatable, Switchable {
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
