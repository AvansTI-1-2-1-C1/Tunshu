package HardwareLayer.Navigation;

import Utils.CallBacks.LineFollowerCallBack;
import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.BoeBot;
import Utils.Enums.LineFollowerValue;

public class LineFollower implements Updatable, Switchable {

    private LineFollowerCallBack lineFollowerCallBack;

    private String sensorName;

    private double lineSensorOutput;

    private int pin;
    private int thresholdValue;

    private boolean isOn;

    public LineFollower(String sensorName, int pin, LineFollowerCallBack lineFollowerCallBack) {

        //To make a callback possible we need to give the interface through the constructor
        // so the class wil know there is a function called lineFollowerCallBack
        this.lineFollowerCallBack = lineFollowerCallBack;
        this.sensorName = sensorName;
        this.pin = pin;
        this.thresholdValue = 1400;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * this function will iterate to update the sensors
     */
    @Override
    public void update() {
        this.lineSensorOutput = BoeBot.analogRead(this.pin);
        lineFollowerCallBack.onLineFollowerStatus(this);
    }

    public String getSensorName() {
        return sensorName;
    }

    public LineFollowerValue getDetectedColor() {
        if ((this.lineSensorOutput < this.thresholdValue) && (this.lineSensorOutput > 0)) {
            return LineFollowerValue.White;
        } else if ((this.lineSensorOutput >= thresholdValue) && (this.lineSensorOutput > 0)) {
            return LineFollowerValue.Black;
        } else {
            return LineFollowerValue.NA;
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
