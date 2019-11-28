package HardwareLayer.Navigation;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class LineFollower implements Updatable, Switchable {

    private int pin;
    private double lineStatus;
    private LineFollowerCallBack lineFollowerCallBack;

    public LineFollower(int pin, LineFollowerCallBack lineFollowerCallBack) {
        this.pin = pin;
        this.lineFollowerCallBack = lineFollowerCallBack;
    }

    private boolean isOn;

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {
        lineFollowerCallBack.onLineFollowerStatus(BoeBot.analogRead(2), BoeBot.analogRead(1), BoeBot.analogRead(0));
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
