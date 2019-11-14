import TI.BoeBot;
import TI.Servo;


public class RobotMain {


    public static void main(String[] args) {

        double centre = 1506.5;
        double speed = 500;

        Servo servoRechts = new Servo(12);
        Servo servoLinks = new Servo(13);

        int i = 0;
        //cool cool cool thing
        while (i<10) {
            Stearing.drivingForward(centre,speed,servoRechts,servoLinks);
            BoeBot.wait(2000);
            Stearing.drivingbackward(centre,speed,servoRechts,servoLinks);
            BoeBot.wait(2000);
            Stearing.stearingRight(centre,speed,servoRechts,servoLinks);
            BoeBot.wait(1000);
            i++;
        }

        //other cool cool thing
        while (i<30){
            Stearing.stearingLeft(centre,speed,servoRechts,servoLinks);
            BoeBot.wait(1000);
            Stearing.drivingForward(centre,speed,servoRechts,servoLinks);
            BoeBot.wait(2000);
            i++;
        }

        servoRechts.stop();
        servoLinks.stop();

    }
}
