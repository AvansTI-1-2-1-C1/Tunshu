package HardwareLayer.Sensor;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class Ultrasonic implements Updatable, Switchable {
    private boolean isOn;
    private double distance;
    private UltrasonicCallBack ultrasonicCallBack;

    public Ultrasonic(UltrasonicCallBack ultrasonicCallBack) {
        this.ultrasonicCallBack = ultrasonicCallBack;
    }

    public void update() {
        BoeBot.digitalWrite(5, false);
        BoeBot.uwait(2);
        BoeBot.digitalWrite(5, true);
        BoeBot.uwait(10);
        BoeBot.digitalWrite(5, false);
        double time = BoeBot.pulseIn(4, true, 3000);
        if (time < 0) {
            this.distance = 44.03;
        }
        this.distance = ((time * 0.034) / 2.0);
        ultrasonicCallBack.ultrasonicSensorDistance(this.distance);
    }

    @Override
    public boolean isOn() {
        return isOn;
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
