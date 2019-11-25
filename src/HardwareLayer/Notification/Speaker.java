package HardwareLayer.Notification;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.PWM;

public class Speaker implements Updatable, Switchable {

    private boolean isOn;
    private int pin;
    private PWM speakerPWM;
    private int speakerFrequency;


    public Speaker( ){

        this.pin = 6;//GPIO pin number for the speaker.
        this.speakerPWM = new PWM( pin, this.speakerFrequency);
        this.speakerFrequency = 0;
    }

    public void speakerFrequencyUpdate( int frequency ){

        this.speakerFrequency = frequency;

    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {

        if ( isOn ){
            this.speakerFrequency = 128;
        }
        else{
            this.speakerFrequency = 0;
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
