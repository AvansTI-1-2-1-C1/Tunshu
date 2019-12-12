package ApplicationLayer;

import HeadInterfaces.Updatable;
import InterfaceLayer.*;
import InterfaceLayer.Override;
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
    private Timer hitDetectionTimer;
    private Timer notificationTimer;


    public void start() {
        //initialise every object
        init();

        setRoute();

        /**
         * detection loop
         */
        while (true) {
            //updates
            for (Updatable updatable : this.updatables) {
                updatable.update();
            }


            if (hitDetectionTimer.timeout()) {
                hitDetection.update();
                hitDetectionTimer.mark();
            }

            //wait so it is less CPU heavy
            if(notificationTimer.timeout()) {
                if (hitDetection.getState()) {
                    notificationSystem.setStatus("alert", true);
                    motorControl.setHandBreak(true);
                    notificationSystem.update();
                } else if (motorControl.isDrivingBackward()) {
                    notificationSystem.setStatus("reverse", false);
                    notificationSystem.update();
                } else if (routeFollower.isLineFollowerState()) {
                    notificationSystem.setStatus("lineFollower", false);
                    notificationSystem.update();
                } else {

                    notificationSystem.setStatus("running", false);
                    notificationSystem.update();
                }
                notificationTimer.mark();
            }
        }
    }

    /**
     * initialising every object we need
     */
    public void init() {
        this.motorControl = new MotorControl();
        this.hitDetection = new HitDetection();
        this.route = new Route();
        this.routeFollower = new RouteFollower(this.motorControl, this.route);
        this.notificationSystem = new NotificationSystem();
        this.override = new Override(this.motorControl, this.notificationSystem, this.hitDetection, this.routeFollower);
        this.hitDetectionTimer = new Timer(50);
        this.notificationTimer = new Timer(100);

        this.updatables = new ArrayList<>();
        this.updatables.add(this.override);
        this.updatables.add(this.hitDetection);
        this.updatables.add(this.routeFollower);
        this.updatables.add(this.motorControl);
    }

    public void setRoute(){
        ArrayList<String> dir = new ArrayList<>();
        dir.add("right");
        dir.add("left");
        this.route.setDirections(dir);
    }

}
