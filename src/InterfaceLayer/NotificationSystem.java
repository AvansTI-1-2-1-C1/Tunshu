package InterfaceLayer;

import HardwareLayer.Notification.LED;
import HardwareLayer.Notification.Speaker;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.Timer;

import java.awt.*;

public class NotificationSystem implements Updatable {
    private LED[] LEDs;
    private Speaker speaker;
    private int status;
    Timer blinkTimer;
    private boolean lichtSwitch;

    /**
     * we call initialise notification system so all the objects and variables are set right
     */
    public NotificationSystem() {
        initNotificationSystem();
    }

    public void initNotificationSystem() {
        //led initialise
        this.LEDs = new LED[6];
        LEDs[0] = new LED(0);
        LEDs[1] = new LED(1);
        LEDs[2] = new LED(2);
        LEDs[3] = new LED(3);
        LEDs[4] = new LED(4);
        LEDs[5] = new LED(5);

        //speaker initialise
        this.speaker = new Speaker();

        //set status to 0
        //status 0 = running
        this.status = 0;

        //timer for how long the lights are on and off
        blinkTimer = new Timer(300);
        //starts the timer
        blinkTimer.mark();

        //boolean for reading if the lights are on or off
        lichtSwitch = true;

    }


    @java.lang.Override
    public void update() {
        BoeBot.rgbShow();

        switch (status) {
            case 0:
                running();
                break;
            case 1:
                alert();
                break;
            case 2:
                reverse();
                break;
            default:
                error();
                break;
        }

    }


    public void running() {
        for (LED led : LEDs) {
            led.setColor(Color.white);
        }
        speaker.speakerFrequencyUpdate(128);
    }


    public void error() {
        if (lichtSwitch){
            for (LED led : LEDs) {
                led.setColor(Color.yellow);
            }
        } else {
            ledOff();
        }
        speaker.speakerFrequencyUpdate(80);
    }


    public void alert() {
        if (lichtSwitch){
            for (LED led : LEDs) {
                led.setColor(Color.red);
            }
        } else {
            ledOff();
        }
        speaker.speakerFrequencyUpdate(128);
    }

    public void reverse(){
        Color reverseColor = new Color(1/40f,1,1);
        for (LED led : LEDs) {
            led.setColor(Color.red);
        }
        speaker.speakerFrequencyUpdate(128);
    }


    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * turns every neopixel on
     */
    public void LEDOn() {
        for (LED led : LEDs) {
            led.on();
        }
    }


    /**
     * turns every neopixel off
     */
    public void ledOff() {
        for (LED led : LEDs) {
            led.off();
        }
    }

    /**
     * turns the speaker on
     */
    public void SpeakerError() {
        while (true) {
            speaker.on();
        }
    }

    public void SpeakerRunning() {
        while (true) {
            speaker.on();
        }
    }

    public void SpeakerAlert() {
        while (true) {
            speaker.on();
            BoeBot.wait(600);
        }
    }

    public void SpeakerReverse() {
        while (true) {
            speaker.on();
            BoeBot.wait(1000);
        }
    }


    /**
     * turns the speaker off
     */
    public void SpeakerOff() {
        speaker.off();
    }

}
