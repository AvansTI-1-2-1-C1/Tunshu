package InterfaceLayer;

import HardwareLayer.Motor;
import HardwareLayer.Navigation.LineFollower;
import HardwareLayer.Navigation.LineFollowerCallBack;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.Timer;

public class RouteFollower extends Drive implements Updatable, LineFollowerCallBack {

    private Drive drive;

    private Motor servoLeft;
    private Motor servoRight;

    private double leftLineStatus;
    private double rightLineStatus;
    private double middleLineStatus;

    private  int counter1;
    private int counter2;
    private int counter3;
    private int counter4;

    private boolean lineFollowerState;

    private LineFollower lineFollower;


    public RouteFollower(Drive drive) {

        //Here the servo's are declared because the line follower actions depend on single servo control
        this.servoLeft = new Motor(12);
        this.servoRight = new Motor(13);

        //To use some of the methods to drive forward easy we associate the drive class
        this.drive = drive;

        //Here the Linefollower is created, this class stands for all three the sensors,
        //we chose this option to make the callbacks and updates more easy and line efficient
        this.lineFollower = new LineFollower(this);

    }

    /**
     * This method is constantly updated
     */
    @java.lang.Override
    public void update() {

        //First there is a check if the function is turned on or not, the function can be turned off in the override
        if (this.lineFollowerState) {
            this.lineFollower.update();

            System.out.println(leftLineStatus);
            System.out.println(middleLineStatus);
            System.out.println(rightLineStatus);

            Timer t4 = new Timer(50);

            //To ensure the Bot does not wiggle too much when following the line
            // we added an timer to round off the edges a bit
            if (t4.timeout()) {


                if (this.middleLineStatus < 1500) {

                    Timer t3 = new Timer(30);

                    if (t3.timeout()) {

                        //If the right sensor detects a line it steers left
                        if (this.rightLineStatus > 1500) {

                            Timer t1 = new Timer(20);

                            if (t1.timeout()) {
                                this.servoLeft.setSpeed(1550 + this.counter1);
                                this.servoRight.setSpeed(1460 + this.counter2);

                                //The longer the middle sensor does not detect a line the more it will steer
                                //again to ensure the Bot does not wiggle too much
                                this.counter1 += 10;
                                this.counter2++;
                                t1.mark();
                            }

                        }
                        //If the left sensor detects a line it steers right
                        if (this.leftLineStatus > 1500) {

                            Timer t2 = new Timer(20);

                            if (t2.timeout()) {
                                this.servoLeft.setSpeed(1540 - this.counter3);
                                this.servoRight.setSpeed(1450 - this.counter4);

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

                this.drive.accelerate(100);

                t4.mark();
            }
        }
    }

    /**
     * If the update function is called the linefollower class wil callback to this method to update the
     * attributes
     */
    public void onLineFollowerStatus(double leftLineFollowerCallBack, double middleLineFollowerCallBack, double rightLineFollowerCallBack){

        //If the update function is called the linefollower class wil callback to this method to update the
        //attributes
        this.leftLineStatus = leftLineFollowerCallBack;
        this.middleLineStatus = middleLineFollowerCallBack;
        this.rightLineStatus = rightLineFollowerCallBack;
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
