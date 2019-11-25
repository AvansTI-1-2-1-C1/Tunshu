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
            drive.control(RemoteControl.detect(0));

        }
    }


    /**
     * initialising every object we need
     */
    public void init(){
        this.drive = new Drive(13, 12);
        this.hitDetection = new HitDetection();
        this.notificationSystem = new NotificationSystem();
        /**
         * @todo
         * hitDetection
         * override
         * route
         * routeFollowe
         */
        this.override = new Override();
        this.route = new Route();
        this.routeFollower = new RouteFollower();
    }


}
