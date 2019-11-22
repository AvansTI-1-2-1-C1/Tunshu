package HardwareLayer.Notification;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class LED implements Updatable, Switchable {
    private boolean isOn;
    private int number;

    public LED(int number) {
        this.number = number;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {
        if (isOn){

        } else {
            BoeBot.rgbSet(number,0,0,0);
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
