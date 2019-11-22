package HardwareLayer.Notification;

import HardwareLayer.HardwareOnAndOff;
import HeadInterfaces.Updatable;

public class LED implements Updatable, HardwareOnAndOff {
    private boolean isOn;

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {
        if (isOn){

        }
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
