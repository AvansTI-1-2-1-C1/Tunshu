package ApplicationLayer;

import InterfaceLayer.*;
import InterfaceLayer.Override;
import TI.BoeBot;
import TI.Timer;
import Utils.Enums.Instructions;
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

    private Timer stateUpdateTimer;

    private States state;

    /**
     * initialising every object we need
     */
    public Tunshu() {
        this.motorControl = new MotorControl();

        this.hitDetection = new HitDetection();
        this.activeLineFollower = new ActiveLineFollower(this.motorControl);
        this.route = new Route();
        this.routeFollower = new RouteFollower(this.motorControl, this.route, this.activeLineFollower);
        this.notificationSystem = new NotificationSystem(this);
        this.override = new Override(this.motorControl, this.notificationSystem, this.routeFollower, this.activeLineFollower);

        this.stateUpdateTimer = new Timer(100);


        state = States.Running;
    }

    /**
     * to start the application
     * here you loop constantly the state that the machine is in
     */
    public void start() {
        //we use this method to set the programmed route
        setRoute();

        while (true) {
            switch (state) {
                case Running:
                    running();
                    break;
                case Reverse:
                    //state reverse only has impact on the notification system so we use the standard running check
                    running();
                    break;
                case Alert:
                    alert();
                    break;
                case Locked:
                    locked();
                    break;
                case LineFollower:
                    lineFollower();
                    break;
                case RouteFollower:
                    routeFollower();
                    break;
                case Error:
                    error();
                    break;
            }

            //makes the application less processing heavy
            BoeBot.wait(20);
        }
    }

    /**
     * BoeBot is updating all the inputs and can be manually driven, set to following a line or set to following a given route
     */
    private void running() {
        //status changer
        if (stateUpdateTimer.timeout()) {
            if (hitDetection.getState()) {
                this.state = States.Alert;
                motorControl.setHandBreak(true);
                notificationSystem.update();
            } else if (motorControl.isDrivingBackward()) {
                this.state = States.Reverse;
                notificationSystem.update();
            } else if (activeLineFollower.isLineFollowerState()) {
                this.state = States.LineFollower;
                notificationSystem.update();
            } else {
                this.state = States.Running;
                notificationSystem.update();
            }
            stateUpdateTimer.mark();
        }

        //updates
        motorControl.update();
        override.update();
        hitDetection.update();
    }

    /**
     * BoeBot is on alert because it detects a dangerous situation
     * state can only be set to locked from here when the situation has been made safe
     */
    private void alert() {
        //status changer
        if (stateUpdateTimer.timeout()) {
            if (hitDetection.getState()) {
                notificationSystem.update();
                override.setUnlocked(false);
            } else {
                this.state = States.Locked;
            }
            stateUpdateTimer.mark();
        }

        //updates
        hitDetection.update();
    }

    /**
     * BoeBot is locked until the remote control unlocks it
     * then the state will be set to running
     */
    private void locked() {
        //status changer
        if (stateUpdateTimer.timeout()) {
            if (hitDetection.getState()) {
                this.state = States.Alert;
                notificationSystem.update();
            } else if (override.isUnlocked()) {
                this.state = States.Running;
                notificationSystem.update();
            }
            stateUpdateTimer.mark();
        }

        //updates
        notificationSystem.update();
        hitDetection.update();
        override.update();
    }

    /**
     * BoeBot is following a line
     */
    private void lineFollower() {
        //status changer
        if (stateUpdateTimer.timeout()) {
            if (hitDetection.getState()) {
                this.state = States.Alert;
                motorControl.setHandBreak(true);
                notificationSystem.update();
            } else if (motorControl.isDrivingBackward()) {
                this.state = States.Reverse;
                notificationSystem.update();
            } else if (activeLineFollower.isLineFollowerState()) {
                this.state = States.LineFollower;
                notificationSystem.update();
            } else {
                this.state = States.Running;
                notificationSystem.update();
            }
            stateUpdateTimer.mark();
        }

        //updates
        hitDetection.update();
        override.update();
        motorControl.update();
        activeLineFollower.update();
    }

    /**
     * BoeBot is following a route
     */
    private void routeFollower() {
        //status changer
        if (stateUpdateTimer.timeout()) {
            if (hitDetection.getState()) {
                this.state = States.Alert;
                motorControl.setHandBreak(true);
                notificationSystem.update();
            } else if (motorControl.isDrivingBackward()) {
                this.state = States.Reverse;
                notificationSystem.update();
            } else if (activeLineFollower.isLineFollowerState()) {
                this.state = States.LineFollower;
                notificationSystem.update();
            } else {
                this.state = States.Running;
                notificationSystem.update();
            }
            stateUpdateTimer.mark();
        }

        //updates
        hitDetection.update();
        override.update();
        motorControl.update();
        activeLineFollower.update();
        routeFollower.update();
    }

    /**
     * something went wrong
     */
    private void error(){
        notificationSystem.update();
    }

    private void setRoute() {
        ArrayList<Instructions> dir = new ArrayList<>();
        dir.add(Instructions.Right);
        dir.add(Instructions.Forward);
        dir.add(Instructions.Left);
        dir.add(Instructions.Forward);
        dir.add(Instructions.Left);
        dir.add(Instructions.Left);
        dir.add(Instructions.Right);
        dir.add(Instructions.Left);
        dir.add(Instructions.Forward);
        dir.add(Instructions.Forward);
        this.route.setInstructions(dir);
    }

    public States getState() {
        return state;
    }
}
