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
            RemoteControl.useButton(drive);

        }
    }


    /**
     * initialising every object we need
     */
    public void init(){
        this.drive = new Drive(12, 13);
        this.notificationSystem = new NotificationSystem();
    }


}
