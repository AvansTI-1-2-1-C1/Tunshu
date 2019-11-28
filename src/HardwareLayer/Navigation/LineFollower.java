package HardwareLayer.Navigation;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class LineFollower implements Updatable, Switchable {

    private LineFollowerCallBack lineFollowerCallBack;

    public LineFollower(LineFollowerCallBack lineFollowerCallBack) {

        //To make a callback possible we need to give the interface through the constructor
        // so the class wil know there is a function called lineFollowerCallBack
        this.lineFollowerCallBack = lineFollowerCallBack;

    }

    private boolean isOn;

    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     this function will iterate to update the sensors
     */
    @Override
    public void update() {
        //this will callback to the RouteFollower class to update the attributes for the line following logic

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
