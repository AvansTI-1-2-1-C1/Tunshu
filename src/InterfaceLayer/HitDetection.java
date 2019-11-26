package InterfaceLayer;

import HardwareLayer.Sensor.Ultrasonic;
import HardwareLayer.Sensor.UltrasonicCallBack;
import HeadInterfaces.Updatable;

public class HitDetection implements Updatable, UltrasonicCallBack {

    private double ultraSonicDistance;
    private Ultrasonic ultrasone;

    public HitDetection() {
        this.ultrasone = new Ultrasonic(this);
    }

    @java.lang.Override
    public void update() {
        ultrasone.update();
    }

    public void ultrasonicSensorDistance(double distance){
        this.ultraSonicDistance = distance;
    }
}
