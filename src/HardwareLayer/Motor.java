package HardwareLayer;

import HeadInterfaces.Updatable;
import TI.Servo;

public class Motor implements Updatable, Switchable {
    private boolean isOn;
    private Servo servo;

    public Motor(int pin) {
        this.servo = new Servo(pin);
        this.servo.start();
    }

    public void setSpeed(int speed){
        this.servo.update(speed);
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
