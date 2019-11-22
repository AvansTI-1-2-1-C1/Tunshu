package InterfaceLayer;

import HardwareLayer.Notification.LED;
import HeadInterfaces.Updatable;
import TI.BoeBot;

import java.awt.*;

public class NotificationSystem implements Updatable {
    private LED[] LEDs;

    public NotificationSystem() {
        initNotificationSystem();
    }

    public void initNotificationSystem() {
        this.LEDs = new LED[6];

        LEDs[0] = new LED(1);
        LEDs[1] = new LED(2);
        LEDs[2] = new LED(3);
        LEDs[3] = new LED(4);
        LEDs[4] = new LED(5);
        LEDs[5] = new LED(6);
    }


    @java.lang.Override
    public void update() {
        BoeBot.rgbShow();
    }


    public void error(){
        for (LED led : LEDs){
            led.setColor(Color.yellow);
        }
    }

    public void alert(){
        for (LED led : LEDs){
            led.setColor(Color.red);
        }
    }


    /**
     * turns every neopixel on
     */
    public void LEDOn(){
        for (LED led : LEDs){
            led.on();
        }
    }


    /**
     * turns every neopixel off
     */
    public void LEDoff(){
        for (LED led : LEDs){
            led.off();
        }
    }

}
