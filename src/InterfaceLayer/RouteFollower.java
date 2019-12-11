package InterfaceLayer;

import HardwareLayer.Navigation.LineFollower;
import HardwareLayer.Navigation.LineFollowerCallBack;
import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.Timer;
import Utils.IntervalTimer;

import java.util.ArrayList;
import java.util.List;

public class RouteFollower implements Updatable, Switchable,LineFollowerCallBack {

    private Timer timer1;
    private Timer t1;
    private Timer t2;
    private Timer t3;
    private Timer t4;

    private Timer turningTimer;
    private Timer intersectionTimer;

    private String currentlyTurningDirection;
    private boolean isTurning;

    private int adjustment;

    private boolean fellOffRight;
    private boolean fellOffLeft;

    private IntervalTimer intervalTimer;

    private MotorControl motorControl;

    private Route route;

    private String leftSensorStatus;
    private String rightSensorStatus;
    private String middleSensorStatus;

    private float counter2;
    private float counter4;

    private float followingSpeed;

    private boolean hasSeenWhite;

    private boolean lineFollowerState;

    private List<LineFollower> lineFollowerList;


    public RouteFollower(MotorControl motorControl, Route route) {

        this.t1 = new Timer(20);
        this.t2 = new Timer(20);
        this.t3 = new Timer(30);
        this.t4 = new Timer(30);
        this.timer1 = new Timer(10);

        this.turningTimer = new Timer(500);

        this.intersectionTimer = new Timer(200);

        this.route = route;

        this.lineFollowerState = true;

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

        this.followingSpeed = 0.3f;

        this.adjustment = 800;

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

        //First there is a check if the function is turned on or not, the function can be turned off in the override
        if (timer1.timeout()) {
            for (LineFollower lineFollower : lineFollowerList) {
                lineFollower.update();

            }
            timer1.mark();
        }

        if (this.lineFollowerState) {
            motorControl.setSlowAccelerate(false);


            //To ensure the Bot does not wiggle too much when following the line
            // we added an timer to round off the edges a bit
            if (t4.timeout()) {


                if (this.middleSensorStatus.equals("white")) {

                    //If the right sensor detects a line it steers left
                    if (this.rightSensorStatus.equals("black") || this.fellOffRight) {
                        this.motorControl.setMotorsTarget(0.2f, 0.5f);
                        this.fellOffRight = true;
                        //The longer the middle sensor does not detect a line the more it will steer
                        //again to ensure the Bot does not wiggle too much
                    }
                    //If the left sensor detects a line it steers right
                    if (this.leftSensorStatus.equals("black") || this.fellOffLeft) {
                        this.motorControl.setMotorsTarget(0.2f, -0.5f);
                        this.fellOffLeft = true;
                        //The longer the middle sensor does not detect a line the more it will steer
                        //again to ensure the Bot does not wiggle too much
                    }

                }else if(this.middleSensorStatus.equals("black")) {

                    this.fellOffLeft = false;
                    this.fellOffRight = false;

                    this.intervalTimer.restart();

                    this.motorControl.setMotorsTarget(0.2f,0);

                    //When the middle line follower detects the line again the steering wil be set back to default
                    this.counter2 = 0.2f;
                    this.counter4 = 0.2f;

                }
                if(this.rightSensorStatus.equals("black") && this.middleSensorStatus.equals("black") && this.leftSensorStatus.equals("black"))


                    t4.mark();
            }
        }else {
            motorControl.setSlowAccelerate(true);
        }

        if(this.hasHitIntersection() && !this.isTurning){
            this.currentlyTurningDirection = this.route.getDirection();
            this.isTurning = true;
            this.intersectionTimer.mark();
        }


        //if the bot is currently turning to another direction on a line grid, you only have to update
        //the routefollower to update the timers and checking for the turning part
        if(intersectionTimer.timeout()) {
            switch (this.currentlyTurningDirection) {
                case "left":
                    this.turnLeft();
                    break;
                case "right":
                    this.turnRight();
                    break;
                case "back":
                    this.turnBack();
                    break;
                case "forward":
                    this.goForward();
                default:
                    break;
            }
        }

    }

    private void turnRight(){

            if(!this.isTurning){
                this.turningTimer.setInterval(200);
                this.isTurning = true;
                this.lineFollowerState = false;
                this.currentlyTurningDirection = "Right";
            }

        this.motorControl.rotate("right");

        for (LineFollower lineFollower : lineFollowerList) {
            lineFollower.update();
        }

        if(this.middleSensorStatus.equals("white")){
            this.hasSeenWhite = true;
            this.turningTimer.mark();
        }

        if (this.hasSeenWhite&&this.middleSensorStatus.equals("black")&&this.turningTimer.timeout()){
            motorControl.setMotorsTarget(0,0);
            this.currentlyTurningDirection = "none";
            this.lineFollowerState = true;
            this.isTurning = false;
            this.hasSeenWhite = false;
            }


    }

    private void turnLeft(){

        while (true){
            if(!this.isTurning){
                this.turningTimer.setInterval(250);
                this.turningTimer.mark();
                this.isTurning = true;
                this.lineFollowerState = false;
            }

            this.motorControl.rotate("left");

            for (LineFollower lineFollower : lineFollowerList) {
                lineFollower.update();
            }
            if (this.turningTimer.timeout()&&this.middleSensorStatus.equals("black")){
                motorControl.setMotorsTarget(0,0);
                this.currentlyTurningDirection = "none";
                this.lineFollowerState = true;
                this.isTurning = false;
                break;
            }
        }
    }

    private void turnBack(){

        while (true){
            if(!this.isTurning){
                this.turningTimer.setInterval(1500);
                this.turningTimer.mark();
                this.lineFollowerState = false;
                this.isTurning = true;
            }

            this.currentlyTurningDirection = "back";

            this.motorControl.rotate("right");
            for (LineFollower lineFollower : lineFollowerList) {
                lineFollower.update();
            }
            if (this.turningTimer.timeout()&&this.middleSensorStatus.equals("black")){
                motorControl.setMotorsTarget(0,0);
                this.currentlyTurningDirection = "none";
                this.lineFollowerState = true;
                this.isTurning = false;
                break;
            }
        }
    }

    private void goForward() {
        if(!this.isTurning){
            this.turningTimer.setInterval(500);
            this.turningTimer.mark();
            this.lineFollowerState = false;
            this.isTurning = true;
        }

        this.currentlyTurningDirection = "forward";

        while (true) {
            this.motorControl.rotate("forward");
            for (LineFollower lineFollower : lineFollowerList) {
                lineFollower.update();
            }
            if (this.turningTimer.timeout() && this.middleSensorStatus.equals("black")) {
                motorControl.setMotorsTarget(0, 0);
                this.currentlyTurningDirection = "none";
                this.lineFollowerState = true;
                this.isTurning = false;
                break;
            }
        }
    }

    /**
     * If the update function is called the linefollower class wil callback to this method to update the
     * attributes
     */
    public void onLineFollowerStatus(LineFollower lineFollower) {

        if (lineFollower.getSensorName().equals("leftSensor")) {
            this.leftSensorStatus = lineFollower.getDetectedColor();
        } else if (lineFollower.getSensorName().equals("middleSensor")) {
            this.middleSensorStatus = lineFollower.getDetectedColor();
        } else if (lineFollower.getSensorName().equals("rightSensor")) {
            this.rightSensorStatus = lineFollower.getDetectedColor();
        }

    }

    public boolean hasHitIntersection(){
        return (this.rightSensorStatus.equals("black") && this.middleSensorStatus.equals("black") && this.leftSensorStatus.equals("black"));
    }


    public boolean isLineFollowerState() {
        return lineFollowerState;
    }



    /**
     * If this function is called the attribute will trigger on
     */
    @java.lang.Override
    public boolean isOn() {
        return false;
    }

        @java.lang.Override
        public void on () {
            this.lineFollowerState = true;
        }

        @java.lang.Override
        public void off () {
            this.lineFollowerState = false;
        }
    }



