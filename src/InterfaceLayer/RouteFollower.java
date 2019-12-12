package InterfaceLayer;

import HardwareLayer.Navigation.LineFollower;
import HardwareLayer.Navigation.LineFollowerCallBack;
import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.Timer;
import Utils.Enums.LineFollowerValue;
import Utils.IntervalTimer;
import Utils.OnOffTimer;

import java.util.ArrayList;
import java.util.List;

public class RouteFollower implements Updatable, Switchable, LineFollowerCallBack {

    private Timer updateDelayTimer;
    //    private Timer t1;
//    private Timer t2;
//    private Timer t3;
    private Timer correctingDelayTimer;

    private Timer turningTimer;
    private Timer intersectionTimer;

    private String currentlyTurningDirection;


    private boolean isTurning;
    private boolean fellOffRight;
    private boolean fellOffLeft;

    private IntervalTimer intervalTimer;

    private MotorControl motorControl;

    private Route route;

    private LineFollowerValue leftSensorStatus;
    private LineFollowerValue rightSensorStatus;
    private LineFollowerValue middleSensorStatus;

    private float counter2;
    private float counter4;

//    private float followingSpeed;

    private boolean hasSeenWhite;

    private boolean lineFollowerState;

    private boolean isFollowingRoute;

    private List<LineFollower> lineFollowerList;


    public RouteFollower(MotorControl motorControl, Route route) {

//        this.t1 = new Timer(20);
//        this.t2 = new Timer(20);
//        this.t3 = new Timer(30);
        this.correctingDelayTimer = new Timer(30);
        this.updateDelayTimer = new Timer(10);

        this.turningTimer = new Timer(500);

        this.intersectionTimer = new Timer(200);

        this.route = route;

        this.lineFollowerState = false;

        this.isFollowingRoute = false;

        this.isTurning = false;

        this.hasSeenWhite = false;

        this.fellOffLeft = false;
        this.fellOffRight = false;

        //Here the motor control wil be implemented
        this.motorControl = motorControl;

        this.intervalTimer = new IntervalTimer();

        //To use some of the methods to drive forward easy we associate the drive class
        this.counter2 = 0.1f;
        this.counter4 = 0.1f;

//        this.followingSpeed = 0.3f;

//        this.adjustment = 800;

        lineFollowerList = new ArrayList<>();

        this.currentlyTurningDirection = "none";


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
        //makes sure the line sensors get updated
        if (updateDelayTimer.timeout()) {
            for (LineFollower lineFollower : lineFollowerList) {
                lineFollower.update();
            }
            updateDelayTimer.mark();
        }

        if (this.hasHitIntersection() && !this.isTurning && this.isFollowingRoute) {
            this.currentlyTurningDirection = this.route.getDirection();
            this.isTurning = true;
            this.lineFollowerState = false;
            this.intersectionTimer.mark();
        }
        if (this.rightSensorStatus.equals("black") && this.middleSensorStatus.equals("black") && this.leftSensorStatus.equals("black")) {
            correctingDelayTimer.mark();
        }

    }


    private void turn() {
        this.motorControl.rotate(this.currentlyTurningDirection);


        if (this.leftSensorStatus.equals("white") && this.middleSensorStatus.equals("white") && this.rightSensorStatus.equals("white")) {
            this.hasSeenWhite = true;
        }

        if (this.hasSeenWhite && this.middleSensorStatus.equals("black")) {
            motorControl.setMotorsTarget(0, 0);
            this.currentlyTurningDirection = "none";
            this.lineFollowerState = true;
            this.isTurning = false;
            this.hasSeenWhite = false;
        }

    }

    public void onLineFollowerStatus(LineFollower lineFollower) {
        switch (lineFollower.getSensorName()){

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

    public boolean hasHitIntersection() {
        return (this.rightSensorStatus.equals("black") && this.middleSensorStatus.equals("black") && this.leftSensorStatus.equals("black"));
    }

    public boolean isLineFollowerState() {
        return lineFollowerState;
    }

    public boolean isFollowingRoute() {
        return isFollowingRoute;
    }

    public void setFollowingRoute(boolean followingRoute) {
        isFollowingRoute = followingRoute;
    }

    /**
     * If this function is called the attribute will trigger on
     */
    @java.lang.Override
    public boolean isOn() {
        return lineFollowerState;
    }

    @java.lang.Override
    public void on() {
        this.lineFollowerState = true;

        this.isTurning = false;

        this.hasSeenWhite = false;

        this.fellOffLeft = false;
        this.fellOffRight = false;
    }

    @java.lang.Override
    public void off() {
        this.lineFollowerState = false;

        this.isTurning = false;

        this.hasSeenWhite = false;

        this.fellOffLeft = false;
        this.fellOffRight = false;
        this.motorControl.setMotorsTarget(0, 0);
    }
}



