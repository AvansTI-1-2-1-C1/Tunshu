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

    /**
     * we call initialise notification system so all the objects and variables are set right
     */
    public NotificationSystem() {
        initNotificationSystem();
    }

    public void initNotificationSystem() {
        //led initialise
        this.LEDs = new LED[5];
        LEDs[0] = new LED(0);
        LEDs[1] = new LED(1);
        LEDs[2] = new LED(2);
        LEDs[3] = new LED(3);
        LEDs[4] = new LED(4);
        LEDs[5] = new LED(5);

        //speaker initialise
        this.speaker = new Speaker();
        this.status = 0;
        blinkTimer = new Timer(100);
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
            default:
                error();
                break;
        }

    }


    public void error() {
        for (LED led : LEDs) {
            led.setColor(Color.yellow);
        }
        speaker.speakerFrequencyUpdate(128);
    }

    public void alert() {
        for (LED led : LEDs) {
            led.setColor(Color.red);
        }
    }

    public void running() {
        for (LED led : LEDs) {
            led.setColor(Color.white);
        }
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
    public void LEDoff() {
        for (LED led : LEDs) {
            led.off();
        }
    }

    /**
     * turns the speaker on
     */
    public void SpeakerOn() {
        while (true) {
            speaker.on();
            BoeBot.wait(800);
        }
    }


    /**
     * turns the speaker off
     */
    public void SpeakerOff() {
        speaker.off();
    }

}
