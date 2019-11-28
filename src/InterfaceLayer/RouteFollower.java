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

    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;

    public RouteFollower(Drive drive) {
        this.drive = drive;

        this.leftLineFollower = new LineFollower(0, this);
        this.middleLineFollower = new LineFollower(1, this);
        this.rightLineFollower = new LineFollower(2, this);

    }

    @java.lang.Override
    public void update() {

        Timer t4 =  new Timer(50);
        if(t4.timeout()) {
            while (BoeBot.analogRead(1) < 1500) {
                Timer t3 = new Timer(30);
                if (t3.timeout()) {
                    int counter4 = 5;
                    int counter2 = 20;
                    while (BoeBot.analogRead(0) > 1500) {
                        Timer t1 = new Timer(20);
                        if (t1.timeout()) {
                            drive.left.setSpeed(1550 + counter2);
                            drive.right.setSpeed(1460 + counter4);
                            counter2 = counter2 + 10;
                            counter4++;
                            t1.mark();
                        }
                    }
                    int counter1 = 20;
                    int counter3 = 5;
                    while (BoeBot.analogRead(2) > 1500) {
                        Timer t2 = new Timer(20);
                        if (t2.timeout()) {
                            drive.left.setSpeed(1540 - counter3);
                            drive.right.setSpeed(1450 - counter1);
                            counter1 = counter1 + 10;
                            counter3++;
                            t2.mark();
                        }
                    }
                    t3.mark();
                }
            }
            this.accelerate(100);
            t4.mark();
        }
    }

    public void onLineFollowerStatus(double lineFollowerData){
//        this.lineFollowerStatus = lineFollowerData;
    }
}
