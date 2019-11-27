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

        //to make sure there is no collision in inputs we will reset the trigger pin by switching the pin false
        BoeBot.digitalWrite(5, false);
        BoeBot.uwait(2);

        //When the trigger pin is reset, the trigger wil be turned true
        // to trigger the sound pulses from the ultrasonic sensor
        BoeBot.digitalWrite(5, true);
        BoeBot.uwait(10);
        BoeBot.digitalWrite(5, false);

        //The time for the sound pulse to reflect into the sensor will be measured here
        // by timing the echo pin to turn true
        double time = BoeBot.pulseIn(4, true, 3000);
        if (time < 0) {

            //After testing we found out the max distance was about 44.03 cm
            this.distance = 44.03;
        }

        //The time will be converted to the distance travelled by the sound pulse
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