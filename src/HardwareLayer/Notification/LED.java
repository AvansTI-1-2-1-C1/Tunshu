package HardwareLayer.Notification;

import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.BoeBot;

import java.awt.*;

public class LED implements Updatable, Switchable {
    private boolean isOn;
    private int number;

    public LED(int number) {
        this.number = number;
        isOn = true;
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
        BoeBot.rgbSet(number, 0, 0, 0);
        isOn = false;
    }

    /**
     * sets the according led to the given color
     *
     * @param color java AWT color needed
     */
    public void setColor(Color color) {
        on();
        BoeBot.rgbSet(number, color);
    }

    /**
     * sets the led to the according rgb color
     *
     * @param red   value from 0-255
     * @param green value from 0-255
     * @param blue  value from 0-255
     */
    public void setColor(int red, int green, int blue) {
        on();
        BoeBot.rgbSet(number, red, green, blue);
    }
}
