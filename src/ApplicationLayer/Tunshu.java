package ApplicationLayer;

import HardwareLayer.RemoteControl;
import InterfaceLayer.*;
import InterfaceLayer.Override;
import TI.BoeBot;

public class Tunshu {
    private Drive drive;
    private HitDetection hitDetection;
    private NotificationSystem notificationSystem;
    private InterfaceLayer.Override override;
    private Route route;
    private RouteFollower routeFollower;

    public void start() {
        //initialise every object
        init();

        /**
         * detection loop
         */
        while (true) {
            BoeBot.wait(0, 20000);
            notificationSystem.update();
            notificationSystem.setStatus(1);
//            RemoteControl.useButton(drive);

        }
    }


    /**
     * initialising every object we need
     */
    public void init(){
        /**
         * @todo
         * hitDetection
         * override
         * route
         * routeFollower
         */
//        this.drive = new Drive(12, 13);
//        this.hitDetection = new HitDetection();
        this.notificationSystem = new NotificationSystem();
//        this.override = new Override();
//        this.route = new Route();
//        this.routeFollower = new RouteFollower();
    }


}
