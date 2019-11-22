package ApplicationLayer;

import HardwareLayer.RemoteControl;
import InterfaceLayer.Drive;
import TI.BoeBot;

public class Tunshu {

    public static void start() {
        init();
        Drive boebot = new Drive(13, 12);
        /**
         * detection loop
         */
        while (true) {
            //BoeBot.wait(0, 20000);
            boebot.control(RemoteControl.detect(0));
        }
    }

    /**
     * initialise all the classes and objects
     */
    public static void init(){

    }
}
