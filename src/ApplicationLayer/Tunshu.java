package ApplicationLayer;

import HardwareLayer.RemoteControl;
import InterfaceLayer.Drive;
import TI.BoeBot;

public class Tunshu {
    private Drive drive;

    public void start() {
        //initialise every object
        init();

        /**
         * detection loop
         */
        while (true) {
            //BoeBot.wait(0, 20000);
            drive.control(RemoteControl.detect(0));
        }
    }


    /**
     * initialising every object we need
     */
    public void init(){
        this.drive = new Drive(13, 12);
    }

}
