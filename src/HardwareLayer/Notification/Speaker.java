package HardwareLayer.Notification;

import Utils.CallBacks.Switchable;
import Utils.CallBacks.Updatable;
import TI.PWM;

public class Speaker implements Updatable, Switchable {

    private boolean isOn;
    private int pin;
    private PWM speakerPWM;
    private int speakerFrequency;


    public Speaker() {

        this.pin = 1;//GPIO pin number for the speaker.
        this.speakerFrequency = 0;
        this.speakerPWM = new PWM(pin, 128);
        this.isOn = false;
    }

    public void speakerFrequencyUpdate(int frequency) {

        this.speakerFrequency = frequency;

    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {

        if (isOn) {
            this.speakerPWM.start();
        } else {
            this.speakerPWM.stop();
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
