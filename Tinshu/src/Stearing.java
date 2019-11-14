
import TI.Servo;

public class Stearing {

    static void drivingForward(double centre, double speed, Servo servoRechts, Servo servoLinks) {
        servoRechts.update((int) (centre - speed));
        servoLinks.update((int) (centre + speed));
    }

    static void drivingbackward(double centre, double speed, Servo servoRechts, Servo servoLinks) {
        servoRechts.update((int) (centre + speed));
        servoLinks.update((int) (centre - speed));
    }

    static void stearingRight(double centre, double speed, Servo servoRechts, Servo servoLinks){
        servoRechts.update((int) (centre));
        servoLinks.update((int) (centre + speed));
    }

    static void stearingLeft(double centre, double speed, Servo servoRechts, Servo servoLinks){
        servoRechts.update((int) (centre - speed));
        servoLinks.update((int) (centre));
    }
}
