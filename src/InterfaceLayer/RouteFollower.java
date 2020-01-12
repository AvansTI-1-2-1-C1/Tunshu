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
    private OnOffTimer activeLineFollowingTimer;

    private Instructions currentlyTurningDirection;

    private ActiveLineFollower activeLineFollower;

    private boolean isTurning;
    private boolean linefollowingStateSetter;

    private MotorControl motorControl;

    private Route route;

    //These are the three objects that resemble the light sensors on the bottom of the bot
    private LineFollowerValue leftSensorStatus;
    private LineFollowerValue rightSensorStatus;
    private LineFollowerValue middleSensorStatus;

    private boolean hasSeenWhite;
    private boolean isFollowingRoute;
    private boolean isTurning180Degrees;

    private List<LineFollower> lineFollowerList;


    public RouteFollower(MotorControl motorControl, Route route, ActiveLineFollower activeLineFollower) {

        this.route = route;
        this.motorControl = motorControl;
        this.activeLineFollower = activeLineFollower;

        this.isFollowingRoute = false;
        this.isTurning = false;
        this.hasSeenWhite = false;
        this.isTurning180Degrees = false;

        this.lineFollowerList = new ArrayList<>();
        this.linefollowingStateSetter = false;

        this.updateDelayTimer = new Timer(50);
        this.correctingDelayTimer = new OnOffTimer(200);
        this.turningTimer = new Timer(250);
        this.forwardDrivingTimer = new Timer(1000);
        this.activeLineFollowingTimer = new OnOffTimer(400);

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

        if (!this.isFollowingRoute) {
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
        if (this.hasHitIntersection() && !this.isTurning) {
            this.currentlyTurningDirection = this.route.getInstruction();
            System.out.println(this.currentlyTurningDirection);
            this.isTurning = true;
            this.activeLineFollower.setLineFollowerState(false);
            System.out.println("LineFollower turned off");
            correctingDelayTimer.setEnabled(true);
            this.forwardDrivingTimer.mark();

            //boolean to make sure it passes the black line twice
            if (this.currentlyTurningDirection == Instructions.Backward)
                this.isTurning180Degrees = true;
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
        this.motorControl.rotate(Instructions.Forward);

        //stop the BoeBot if it needs to be stopped
        if (this.currentlyTurningDirection == Instructions.Stop) {
            this.motorControl.rotate(this.currentlyTurningDirection);
            this.isTurning=false;
            return;
        }

        //skip the intersection if the instruction is forward
        if (correctingDelayTimer.timeout()) {
            if (this.currentlyTurningDirection == Instructions.Forward) {
                this.isTurning = false;
                this.activeLineFollowingTimer.setEnabled(true);
                return;
            }
            this.motorControl.rotate(this.currentlyTurningDirection);
            this.correctingDelayTimer.setEnabled(false);
            this.turningTimer.mark();
        }

        //setter for has seen white
        if ((this.leftSensorStatus == LineFollowerValue.White) &&
                (this.middleSensorStatus == LineFollowerValue.White) &&
                (this.rightSensorStatus == LineFollowerValue.White) && this.turningTimer.timeout()) {
            System.out.println("white");
            this.hasSeenWhite = true;
        }

        //if the middle sensors sees the black line it is done turning or it needs to go to the next black line
        if (this.hasSeenWhite && (this.middleSensorStatus == LineFollowerValue.Black)) {
            if (!this.isTurning180Degrees) {
                this.currentlyTurningDirection = Instructions.None;
                this.isTurning = false;
                this.hasSeenWhite = false;
                this.activeLineFollower.setLineFollowerState(true);
                this.motorControl.setTurning(false);
                System.out.println("done turning");
            }else {
                this.hasSeenWhite = false;
                this.isTurning180Degrees=false;
            }
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



