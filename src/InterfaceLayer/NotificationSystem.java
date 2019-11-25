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
    private Timer blinkTimer;
    private Timer reverseTimer;
    private boolean lichtSwitch;
    private boolean reverseBeepInterval;

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

        reverseTimer = new Timer(1200);
        reverseTimer.mark();

        reverseBeepInterval = true;

    }


    @java.lang.Override
    public void update() {
        BoeBot.rgbShow();
        if (blinkTimer.timeout()){
            lichtSwitch = !lichtSwitch;
        }
        if (reverseTimer.timeout()){
            reverseBeepInterval = !reverseBeepInterval;
        }

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
        speaker.speakerFrequencyUpdate(80);
        for (LED led : LEDs) {
            led.setColor(Color.white);
        }
        speaker.on();
    }


    public void error() {
        speaker.speakerFrequencyUpdate(128);
        if (lichtSwitch){
            for (LED led : LEDs) {
                led.setColor(Color.yellow);
            }
            speaker.on();
        } else {
            ledOff();
        }
    }


    public void alert() {
        speaker.speakerFrequencyUpdate(128);
        if (lichtSwitch){
            for (LED led : LEDs) {
                led.setColor(Color.red);
            }
            speaker.on();
        } else {
            ledOff();
        }

    }

    public void reverse(){
        Color reverseColor = new Color(1/40f,1,1);
        speaker.speakerFrequencyUpdate(80);
        if ( reverseBeepInterval ){
            for (LED led : LEDs) {
                led.setColor(Color.red);
            }
            speaker.on();
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
    public void ledOff() {
        for (LED led : LEDs) {
            led.off();
        }
    }


}
