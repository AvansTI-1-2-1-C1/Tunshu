package InterfaceLayer;

import HardwareLayer.Notification.LED;
import HardwareLayer.Notification.Speaker;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.PWM;
import TI.Timer;

import java.awt.*;

public class NotificationSystem implements Updatable {
    /**
     * status codes:
     *  0: running normally
     *  1: alert
     *  2: reverse
     *  else: error
     */

    private LED[] LEDs;
    private Speaker speaker;
    private int status;
    private Timer blinkTimer;
    private boolean lightSwitch;
    private Timer reverseTimer;

    /**
     * we call initialise notification system so all the objects and variables are set right
     */
    public NotificationSystem() {
        initNotificationSystem();
    }

    private void initNotificationSystem() {
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
        blinkTimer = new Timer(100);
        //timer for the length of the beeps and lights when reversing
        reverseTimer = new Timer(500);

        //starts the timers
        blinkTimer.mark();
        reverseTimer.mark();

        //boolean for reading if the lights are on or off
        lightSwitch = true;

    }


    @java.lang.Override
    public void update() {
        //update all the leds
        BoeBot.rgbShow();
        //update the speaker
        speaker.update();

        //switch that selects which method needs to be run
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


    private void running() {
        LEDs[5].setColor(Color.white);
        LEDs[3].setColor(Color.white);

        LEDs[0].setColor(155, 0, 0);
        LEDs[2].setColor(155, 0, 0);
    }


    private void error() {
        speaker.speakerFrequencyUpdate(128);
        if (lightSwitch) {
            for (LED led : LEDs) {
                led.setColor(Color.yellow);
            }
            speaker.off();
        } else {
            ledOff();
        }
    }

    /**
     * alert means there is something and then it flashed the lights red and of and sounds a beep
     */
    private void alert() {
        //set the tone of the beep
        speaker.speakerFrequencyUpdate(128);
        //if the time has ended switch the light boolean
        if (blinkTimer.timeout()) {
            lightSwitch = !lightSwitch;

            //turns the leds to the corresponding collor
            if (lightSwitch) {
                for (LED led : LEDs) {
                    led.setColor(Color.red);
                }
                speaker.on();
            } else {
                ledOff();
                speaker.off();
            }

            //start the timer again
            blinkTimer.mark();
        }




    }

    /**
     * reverse means the middle back light goes on and turns red
     */
    private void reverse() {
        //set the tone of the beep
        speaker.speakerFrequencyUpdate(80);
        //if the time has passed switch the light
        if (reverseTimer.timeout()) {
            lightSwitch = !lightSwitch;
            if (lightSwitch) {
                LEDs[0].on();
                LEDs[1].on();
                LEDs[2].on();
                LEDs[0].setColor(Color.red);
                LEDs[1].setColor(Color.red);
                LEDs[2].setColor(Color.red);
                speaker.on();
            } else {
                LEDs[0].off();
                LEDs[1].off();
                LEDs[2].off();
                speaker.off();
            }
            reverseTimer.mark();
        }


    }

    /**
     * sets the status of the boebot
     * @param status
     * status codes:
     *  0: running normally
     *  1: alert
     *  2: reverse
     *  else: error
     */
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
