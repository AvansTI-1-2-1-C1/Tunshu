package InterfaceLayer;

import HardwareLayer.Sensor.UltrasonicCallBack;
import HeadInterfaces.Updatable;

public class HitDetection implements Updatable, UltrasonicCallBack {

    private double ultraSonicDistance;

    @java.lang.Override
    public void update() {

    }

    public void ultrasonicSensorDistance(double distance){
        this.ultraSonicDistance = distance;
    }
}
