package HardwareLayer.Sensor;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class Ultrasone implements Updatable, Switchable {
    private boolean isOn;

    public static double ultrasonicSensorDistance() {
        BoeBot.digitalWrite(5, false);
        BoeBot.uwait(2);
        BoeBot.digitalWrite(5, true);
        BoeBot.uwait(10);
        BoeBot.digitalWrite(5, false);
        double time = BoeBot.pulseIn(4, true, 3000);
        if (time < 0) {
            return 44.03;
        }
        return ((time * 0.034) / 2.0);
    }

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
