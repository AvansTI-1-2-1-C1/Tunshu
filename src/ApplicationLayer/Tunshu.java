package ApplicationLayer;

import HardwareLayer.Notification.Speaker;
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
            //updates
            hitDetection.update();
            notificationSystem.update();
            override.update();
            route.update();
            routeFollower.update();
            //wait so it is less CPU heavy
            BoeBot.wait(2);

            //tests
            RemoteControl.useButton(drive, notificationSystem);
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
        this.drive = new Drive();
        this.hitDetection = new HitDetection();
        this.notificationSystem = new NotificationSystem();
        this.override = new Override();
        this.route = new Route();
        this.routeFollower = new RouteFollower();
    }


}
