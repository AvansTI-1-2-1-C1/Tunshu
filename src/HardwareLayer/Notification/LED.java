package HardwareLayer.Notification;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

import java.awt.*;

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
        if (isOn) {
            BoeBot.rgbShow();
        } else {
            BoeBot.rgbSet(number, 0, 0, 0);
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


    public void setColor(Color color) {
        BoeBot.rgbSet(number, color);
    }

    public void setColor(int red, int green, int blue) {
        BoeBot.rgbSet(number, red,green,blue);
    }
}
