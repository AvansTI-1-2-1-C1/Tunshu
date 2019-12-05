package InterfaceLayer;

import HardwareLayer.Motor;
import HardwareLayer.Navigation.LineFollower;
import HardwareLayer.Navigation.LineFollowerCallBack;
import HeadInterfaces.Updatable;
import TI.Timer;

import java.util.ArrayList;
import java.util.List;

public class RouteFollower implements Updatable, LineFollowerCallBack {

    private Drive drive;

    private Timer timer1;
    private Timer t1;
    private Timer t2;
    private Timer t3;
    private Timer t4;

    private MotorControl motorControl;

    private String leftSensorStatus;
    private String rightSensorStatus;
    private String middleSensorStatus;

    private  int counter1;
    private int counter2;
    private int counter3;
    private int counter4;

    private boolean lineFollowerState;

    private List<LineFollower> lineFollowerList;


    public RouteFollower(MotorControl motorControl) {

        this.t1 = new Timer(20);
        this.t2 = new Timer(20);
        this.t3 = new Timer(30);
        this.t4 = new Timer(50);
        this.timer1 = new Timer(10);

        this.lineFollowerState = false;
        //Here the motor control wil be implemented
        this.motorControl = motorControl;

        //To use some of the methods to drive forward easy we associate the drive class
        this.drive = drive;

        lineFollowerList = new ArrayList<>();

        //Here the Line follower is created, this class stands for all three the sensors,
        //we chose this option to make the callbacks and updates more easy and line efficient
        lineFollowerList.add(new LineFollower("leftSensor",2, this));
        lineFollowerList.add(new LineFollower("middleSensor", 1,this));
        lineFollowerList.add(new LineFollower("rightSensor", 0,this));

        motorControl.setMotorsTarget(0.2f,0);

    }

    /**
     * This method is constantly updated
     */
    @java.lang.Override
    public void update() {

        //First there is a check if the function is turned on or not, the function can be turned off in the override
        if (this.lineFollowerState) {

            if(timer1.timeout()) {
                for (LineFollower lineFollower : lineFollowerList) {
                    lineFollower.update();

                }
                timer1.mark();
            }

            //To ensure the Bot does not wiggle too much when following the line
            // we added an timer to round off the edges a bit
            if (t4.timeout()) {


                if (this.middleSensorStatus.equals("white")) {

                    if (t3.timeout()) {

                        //If the right sensor detects a line it steers left
                        if (this.rightSensorStatus.equals("black")) {


                            if (t1.timeout()) {

                                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), 10+this.counter2);

                                //The longer the middle sensor does not detect a line the more it will steer
                                //again to ensure the Bot does not wiggle too much
                                this.counter1 += 10;
                                this.counter2++;
                                t1.mark();
                            }

                        }
                        //If the left sensor detects a line it steers right
                        if (this.leftSensorStatus.equals("black")) {



                            if (t2.timeout()) {
                                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), -10-this.counter4);

                                //The longer the middle sensor does not detect a line the more it will steer
                                //again to ensure the Bot does not wiggle too much
                                this.counter3 += 10;
                                this.counter4++;
                                t2.mark();
                            }

                        }

                        t3.mark();

                    }

                } else {


                    //When the middle line follower detects the line again the steering wil be set back to default
                    this.counter1 = 20;
                    this.counter2 = 5;

                    this.counter3 = 20;
                    this.counter4 = 5;

                }

                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(),0);

                t4.mark();
            }
        }
    }

    /**
     * If the update function is called the linefollower class wil callback to this method to update the
     * attributes
     */
    public void onLineFollowerStatus(LineFollower lineFollower){

        if(lineFollower.getSensorName().equals("leftSensor")){
            this.leftSensorStatus = lineFollower.getDetectedColor();
        } else if(lineFollower.getSensorName().equals("middleSensor")){
            this.middleSensorStatus = lineFollower.getDetectedColor();
        } else if(lineFollower.getSensorName().equals("rightSensor")){
            this.rightSensorStatus = lineFollower.getDetectedColor();
        }

    }

    /**
     * If this function is called the attribute will trigger on
     */
    public void turnOn(){

        this.lineFollowerState = true;
    }

    /**
     * If this function is called the attribute will trigger off
     */
    public void turnOff(){

        this.lineFollowerState = false;
    }
}
