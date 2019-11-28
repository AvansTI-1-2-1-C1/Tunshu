package InterfaceLayer;

import HardwareLayer.Navigation.LineFollower;
import HardwareLayer.Navigation.LineFollowerCallBack;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.Timer;

public class RouteFollower extends Drive implements Updatable, LineFollowerCallBack {

    private Drive drive;

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

        this.drive = drive;

        this.lineFollower = new LineFollower(this);

    }

    @java.lang.Override
    public void update() {
        if (this.lineFollowerState) {
            this.lineFollower.update();

            System.out.println(leftLineStatus);
            System.out.println(middleLineStatus);
            System.out.println(rightLineStatus);

            Timer t4 = new Timer(50);

            if (t4.timeout()) {


                if (this.middleLineStatus < 1500) {

                    Timer t3 = new Timer(30);

                    if (t3.timeout()) {

                        if (this.rightLineStatus > 1500) {

                            Timer t1 = new Timer(20);

                            if (t1.timeout()) {
                                this.drive.left.setSpeed(1550 + this.counter1);
                                this.drive.right.setSpeed(1460 + this.counter2);
                                this.counter1 += 10;
                                this.counter2++;
                                t1.mark();
                            }

                        }

                        if (this.leftLineStatus > 1500) {

                            Timer t2 = new Timer(20);

                            if (t2.timeout()) {
                                this.drive.left.setSpeed(1540 - this.counter3);
                                this.drive.right.setSpeed(1450 - this.counter4);
                                this.counter3 += 10;
                                this.counter4++;
                                t2.mark();
                            }

                        }

                        t3.mark();
                    }

                } else {
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

    public void onLineFollowerStatus(double leftLineFollowerCallBack, double middleLineFollowerCallBack, double rightLineFollowerCallBack){
        this.leftLineStatus = leftLineFollowerCallBack;
        this.middleLineStatus = middleLineFollowerCallBack;
        this.rightLineStatus = rightLineFollowerCallBack;
    }

    public void turnOn(){
        this.lineFollowerState = true;
    }
    public void turnOff(){
        this.lineFollowerState = false;
    }
}
