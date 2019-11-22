package ApplicationLayer;

import HardwareLayer.RemoteControl;
import InterfaceLayer.Drive;
import InterfaceLayer.NotificationSystem;
import TI.BoeBot;

public class Tunshu {
    private Drive drive;
    private NotificationSystem notificationSystem;

    public void start() {
        //initialise every object
        init();

        /**
         * detection loop
         */
        while (true) {
            BoeBot.wait(0, 20000);
            notificationSystem.update();
            drive.control(RemoteControl.detect(0));

        }
    }


    /**
     * initialising every object we need
     */
    public void init(){
        this.drive = new Drive(13, 12);
        this.notificationSystem = new NotificationSystem();
    }


}
