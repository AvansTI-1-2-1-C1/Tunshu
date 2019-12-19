package ApplicationLayer;

import InterfaceLayer.HeadInterfaces.Updatable;
import InterfaceLayer.*;
import InterfaceLayer.Override;
import TI.BoeBot;
import TI.Timer;
import Utils.Enums.Directions;
import Utils.Enums.States;

import java.util.ArrayList;

public class Tunshu {

    private MotorControl motorControl;
    private HitDetection hitDetection;
    private NotificationSystem notificationSystem;
    private InterfaceLayer.Override override;
    private Route route;
    private ActiveLineFollower activeLineFollower;
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

            for (Updatable updatable : this.updatables) {
                updatable.update();
            }
            if (hitDetectionTimer.timeout()) {
                hitDetection.update();
                activeLineFollower.update();
                motorControl.update();
                routeFollower.update();
                hitDetectionTimer.mark();
            }

            if(notificationTimer.timeout()) {
                if (hitDetection.getState()) {
                    notificationSystem.setStatus(States.Alert, true);
                    motorControl.setHandBreak(true);
                    notificationSystem.update();
                } else if (motorControl.isDrivingBackward()) {
                    notificationSystem.setStatus(States.Reverse, false);
                    notificationSystem.update();
                } else if (activeLineFollower.isLineFollowerState()) {
                    notificationSystem.setStatus(States.LineFollower, false);
                    notificationSystem.update();
                } else {

                    notificationSystem.setStatus(States.Running, false);
                    notificationSystem.update();
                }
                notificationTimer.mark();
            }
            BoeBot.wait(10);
        }
    }

    /**
     * initialising every object we need
     */
    public void init() {
        this.motorControl = new MotorControl();

        this.hitDetection = new HitDetection();
        this.activeLineFollower = new ActiveLineFollower(this.motorControl);
        this.route = new Route();
        this.routeFollower = new RouteFollower(this.motorControl, this.route, this.activeLineFollower);
        this.notificationSystem = new NotificationSystem();
        this.override = new Override(this.motorControl, this.notificationSystem, this.hitDetection, this.routeFollower, this.activeLineFollower);

        this.hitDetectionTimer = new Timer(20);
        this.notificationTimer = new Timer(100);

        this.activeLineFollower.setLineFollowerState(true);

        this.updatables = new ArrayList<>();
        this.updatables.add(this.override);
        this.updatables.add(this.routeFollower);
        this.updatables.add(this.activeLineFollower);

    }

    public void setRoute() {
        ArrayList<Directions> dir = new ArrayList<>();
        dir.add(Directions.Right);
        dir.add(Directions.Forward);
        dir.add(Directions.Left);
        dir.add(Directions.Forward);
        dir.add(Directions.Left);
        dir.add(Directions.Left);
        dir.add(Directions.Right);
        dir.add(Directions.Left);
        dir.add(Directions.Forward);
        dir.add(Directions.Forward);
        this.route.setDirections(dir);
    }

}
