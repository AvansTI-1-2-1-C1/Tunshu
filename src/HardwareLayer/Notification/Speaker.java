package HardwareLayer.Notification;

import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.PWM;

public class Speaker implements Updatable, Switchable {

    private boolean isOn;
    private int pin;
    private PWM speakerPWM;
    private int speakerFrequency;


    public Speaker() {

        this.pin = 7;//GPIO pin number for the speaker.
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

    /**
     * the update method will pwm the new
     */
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
