package InterfaceLayer;

import HardwareLayer.Navigation.LineFollower;
import Utils.CallBacks.LineFollowerCallBack;
import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.Timer;
import Utils.Enums.Instructions;
import Utils.Enums.LineFollowerValue;
import Utils.OnOffTimer;

import java.util.ArrayList;
import java.util.List;

public class RouteFollower implements Updatable, Switchable, LineFollowerCallBack {

    private Timer updateDelayTimer;
    private Timer turningTimer;
    private Timer forwardDrivingTimer;
    private OnOffTimer correctingDelayTimer;

    private Instructions currentlyTurningDirection;

    private ActiveLineFollower activeLineFollower;

    private boolean isTurning;

    private MotorControl motorControl;

    private Route route;

    //These are the three objects that resemble the light sensors on the bottom of the bot
    private LineFollowerValue leftSensorStatus;
    private LineFollowerValue rightSensorStatus;
    private LineFollowerValue middleSensorStatus;

    private boolean hasSeenWhite;
    private boolean isFollowingRoute;

    private List<LineFollower> lineFollowerList;


    public RouteFollower(MotorControl motorControl, Route route, ActiveLineFollower activeLineFollower) {

        this.route = route;
        this.motorControl = motorControl;
        this.activeLineFollower = activeLineFollower;

        this.isFollowingRoute = false;
        this.isTurning = false;
        this.hasSeenWhite = false;

        lineFollowerList = new ArrayList<>();

        this.updateDelayTimer = new Timer(50);
        this.correctingDelayTimer = new OnOffTimer(100);
        this.turningTimer = new Timer(250);
        this.forwardDrivingTimer = new Timer(1000);

        //Here the Line follower is created, this class stands for all three the sensors,
        //we chose this option to make the callbacks and updates more easy and line efficient
        lineFollowerList.add(new LineFollower("leftSensor", 2, this));
        lineFollowerList.add(new LineFollower("middleSensor", 1, this));
        lineFollowerList.add(new LineFollower("rightSensor", 0, this));

    }

    /**
     * This method is constantly updated
     */
    @java.lang.Override
    public void update() {

        if (!this.isFollowingRoute){
            return;
        }

        //makes sure the line sensors get updated
        if (updateDelayTimer.timeout()) {
            for (LineFollower lineFollower : lineFollowerList) {
                lineFollower.update();
            }
            updateDelayTimer.mark();
        }

        /*
         if the bot sees a intersection, and is not allready turning and the routefollowing is turned on
         then the bot wil start rotating into the right direction
         */
        if (this.hasHitIntersection() && !this.isTurning && this.isFollowingRoute) {
            this.currentlyTurningDirection = this.route.getInstruction();
            System.out.println(this.currentlyTurningDirection);
            this.isTurning = true;
            this.activeLineFollower.setLineFollowerState(false);
            System.out.println("linefollower uit");
            correctingDelayTimer.setEnabled(true);
            this.forwardDrivingTimer.mark();
        }

        // if the turning sequence has been set true, then the turn method will be constantly updated
        if (this.isTurning) {
            this.turn();
        }
    }

    /**
     * this is the method that manages the turning
     */
    private void turn() {

        //the first statement is to make sure that the forward instruction will be ignored
        if (currentlyTurningDirection != Instructions.Forward) {
            if (correctingDelayTimer.timeout()) {
                this.motorControl.rotate(this.currentlyTurningDirection);
                this.correctingDelayTimer.setEnabled(false);
                this.turningTimer.mark();
            }

            if ((this.leftSensorStatus == LineFollowerValue.White) &&
                    (this.middleSensorStatus == LineFollowerValue.White) &&
                    (this.rightSensorStatus == LineFollowerValue.White) && this.turningTimer.timeout()) {
                System.out.println("white");
                this.hasSeenWhite = true;
            }

            if (this.hasSeenWhite && (this.middleSensorStatus == LineFollowerValue.Black)) {
                this.currentlyTurningDirection = Instructions.None;
                this.isTurning = false;
                this.hasSeenWhite = false;
                this.activeLineFollower.setLineFollowerState(true);
                this.motorControl.setTurning(false);
                System.out.println("done turning");
            }
        } else if (this.forwardDrivingTimer.timeout()) {
            this.isTurning = false;
        }

    }

    /**
     * this method wil check if the intersection has been hit
     *
     * @return if so it will return true else false
     */
    private boolean hasHitIntersection() {
        return ((this.middleSensorStatus == LineFollowerValue.Black) &&
                (this.leftSensorStatus == LineFollowerValue.Black) &&
                (this.rightSensorStatus == LineFollowerValue.Black));
    }

    public void setTurning(boolean turning) {
        isTurning = turning;
    }

    @java.lang.Override
    public boolean isOn() {
        return this.isFollowingRoute;
    }

    @java.lang.Override
    public void on() {
        this.isFollowingRoute = true;
        this.isTurning = false;
        this.hasSeenWhite = false;
    }

    @java.lang.Override
    public void off() {
        this.isFollowingRoute = false;
        this.isTurning = false;
        this.hasSeenWhite = false;
    }

    /**
     * callback from the LineFollower class
     *
     * @param lineFollower the callback returns an object, we specify each sensor by switching on the objects name
     */
    public void onLineFollowerStatus(LineFollower lineFollower) {
        switch (lineFollower.getSensorName()) {

            case "leftSensor":
                this.leftSensorStatus = lineFollower.getDetectedColor();
                break;
            case "middleSensor":
                this.middleSensorStatus = lineFollower.getDetectedColor();
                break;
            case "rightSensor":
                this.rightSensorStatus = lineFollower.getDetectedColor();
                break;
        }
    }
}



