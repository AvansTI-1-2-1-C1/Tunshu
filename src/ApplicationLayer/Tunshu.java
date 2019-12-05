package ApplicationLayer;

import HeadInterfaces.Updatable;
import InterfaceLayer.*;
import InterfaceLayer.Override;
import TI.BoeBot;
import TI.Timer;

import java.util.ArrayList;

public class Tunshu {
    private MotorControl motorControl;
    private HitDetection hitDetection;
    private NotificationSystem notificationSystem;
    private InterfaceLayer.Override override;
    private Route route;
    private RouteFollower routeFollower;
    private ArrayList<Updatable> updatables;


    public void start() {
        //initialise every object
        init();

        /**
         * detection loop
         */
        while (true) {
            Timer hitDetectionTimer = new Timer(50);
            //updates
            for (Updatable updatable : this.updatables) {
                updatable.update();
            }


            if (hitDetectionTimer.timeout()) {
                hitDetection.update();
                hitDetectionTimer.mark();
            }

            //wait so it is less CPU heavy
            BoeBot.wait(2);

            if (hitDetection.getState()) {
                notificationSystem.setStatus("alert");

//            } else if (drive.isBackwards()) {
//                notificationSystem.setStatus("reverse");
            } else {
                notificationSystem.setStatus("running");
            }
        }
    }

    /**
     * initialising every object we need
     */
    public void init() {
        /**
         * @todo
         * route
         * routeFollower
         */
        this.motorControl = new MotorControl();
        this.hitDetection = new HitDetection();
        this.routeFollower = new RouteFollower(this.motorControl);
        this.notificationSystem = new NotificationSystem();
        this.override = new Override(this.motorControl, this.notificationSystem, this.hitDetection, this.routeFollower);
        this.route = new Route();
        this.updatables = new ArrayList<>();
        this.updatables.add(this.hitDetection);
        this.updatables.add(this.notificationSystem);
        this.updatables.add(this.override);
        this.updatables.add(this.route);
        this.updatables.add(this.routeFollower);
        this.updatables.add(this.motorControl);
    }


}
