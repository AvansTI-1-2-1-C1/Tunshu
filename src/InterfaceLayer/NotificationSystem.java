package InterfaceLayer;

import ApplicationLayer.Tunshu;
import HardwareLayer.Notification.LED;
import HardwareLayer.Notification.Speaker;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.Timer;
import Utils.Enums.States;

import java.awt.*;


public class NotificationSystem implements Updatable {

    private LED[] LEDs;
    private Speaker speaker;
    private States status;
    private Timer blinkTimer;
    private boolean lightSwitch;
    private Timer reverseTimer;
    private boolean isMuted;
    private Timer ledUpdateTimer;
    private Timer statusSwitchTimer;

    private Tunshu tunshu;


    /**
     * we call initialise notification system so all the objects and variables are set right
     */
    public NotificationSystem(Tunshu tunshu) {
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
        this.status = States.Running;

        //reSetStatusTimer for how long the lights are on and off
        blinkTimer = new Timer(100);
        //reSetStatusTimer for the length of the beeps and lights when reversing
        reverseTimer = new Timer(500);

        //starts the timers
        blinkTimer.mark();
        reverseTimer.mark();

        //boolean for reading if the lights are on or off
        lightSwitch = true;

        //boolean to know if the speaker has to be muted
        isMuted = true;

        ledUpdateTimer = new Timer(100);
        statusSwitchTimer = new Timer(200);

        //set the state
        this.tunshu = tunshu;
        this.status = tunshu.getState();
    }


    @java.lang.Override
    public void update() {
        //update all the leds
        if (ledUpdateTimer.timeout()) {
            BoeBot.rgbShow();
            ledUpdateTimer.mark();
        }
        //update the speaker
        speaker.update();

        //switch that selects which method needs to be run
        if (statusSwitchTimer.timeout()) {
            this.status = tunshu.getState();
            switch (status) {
                case Running:
                    running();
                    break;
                case Alert:
                    alert();
                    break;
                case Reverse:
                    reverse();
                    break;
                case RouteFollower:
                    lineFollower();
                    break;
                case Locked:
                    locked();
                    break;
                case Error:
                    error();
                    break;
            }
        }

    }

    /**
     * the normal status
     * 2 lights in front white
     * 2 lights in the back red
     * other lights off
     */
    private void running() {
        LEDs[5].setColor(Color.white);
        LEDs[3].setColor(Color.white);

        LEDs[0].setColor(155, 0, 0);
        LEDs[2].setColor(155, 0, 0);

        LEDs[1].off();
        LEDs[4].off();

        if (!isMuted) {
            speaker.on();
        } else {
            speaker.off();
        }
    }


    private void lineFollower() {
        LEDs[5].setColor(Color.white);
        LEDs[3].setColor(Color.white);

        LEDs[0].setColor(155, 0, 0);
        LEDs[2].setColor(155, 0, 0);

        LEDs[1].setColor(Color.BLUE);
        LEDs[4].setColor(Color.BLUE);

        if (!isMuted) {
            speaker.on();
        } else {
            speaker.off();
        }
    }

    /**
     * if a status is called that is unknown
     */
    private void error() {
        //set the tone of the beep
        speaker.speakerFrequencyUpdate(128);

        //sets all the lights to orange
        for (LED led : LEDs) {
            led.setColor(Color.yellow);
        }

        //if isMuted speaker is turned of else it is tuned on
        if (!isMuted) {
            speaker.on();
        } else {
            speaker.off();
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
                //make sure the speaker is on
                speaker.on();

            } else {
                LEDOff();
                speaker.off();
            }

            //start the reSetStatusTimer again
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
                LEDs[3].on();
                LEDs[5].on();

                LEDs[0].setColor(Color.red);
                LEDs[1].setColor(Color.red);
                LEDs[2].setColor(Color.red);

                LEDs[3].setColor(Color.white);
                LEDs[4].off();
                LEDs[5].setColor(Color.white);

                speaker.on();
//                //if isMuted speaker is turned of else it is tuned on
//                if (!isMuted){
//                    speaker.on();
//                }else {
//                    speaker.off();
//                }

            } else {
                LEDs[0].off();
                LEDs[1].off();
                LEDs[2].off();
                speaker.off();
            }
            reverseTimer.mark();
        }


    }

    private void locked() {
        LEDs[5].setColor(Color.white);
        LEDs[3].setColor(Color.white);

        LEDs[0].setColor(155, 0, 0);
        LEDs[2].setColor(155, 0, 0);

        LEDs[1].setColor(Color.yellow);
        LEDs[4].setColor(Color.yellow);

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
    public void LEDOff() {
        for (LED led : LEDs) {
            led.off();
        }
    }

    public void toggleLED() {
        if (lightSwitch) {
            LEDOff();
        } else {
            LEDOn();
        }
    }

    /**
     * mutes the speaker when the boolean is true
     */
    public void mute() {

        isMuted = !isMuted;
        if (!isMuted) {
            speaker.off();
        } else {
            speaker.on();
        }
    }

    public void speakerOn() {
        speaker.on();
    }

    public void speakerOff() {
        speaker.off();
    }

    public boolean getSound() {
        return !this.isMuted;
    }

    public boolean getLight() {
        return this.lightSwitch;
    }
}
