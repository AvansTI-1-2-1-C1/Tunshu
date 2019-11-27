package InterfaceLayer;

import HardwareLayer.Sensor.Antenna;
import HardwareLayer.Sensor.AntennaCallBack;
import HardwareLayer.Sensor.Ultrasonic;
import HardwareLayer.Sensor.UltrasonicCallBack;
import HeadInterfaces.Updatable;

public class HitDetection implements Updatable, UltrasonicCallBack, AntennaCallBack {

    private Ultrasonic ultrasonic;
    private Antenna antenna;

    private double ultraSonicDistance;
    private boolean antennaState;

    private boolean hitDetectionState;

    public HitDetection() {

        this.ultrasonic = new Ultrasonic(this);
        this.antenna = new Antenna(this);

    }

    @java.lang.Override
    public void update() {

        ultrasonic.update();
        antenna.update();

        hitDetectionState = this.ultraSonicDistance < 10 || this.antennaState;

    }

    public boolean getState(){

        return hitDetectionState;

    }

    public void ultrasonicSensorDistance(double distance){
        this.ultraSonicDistance = distance;
    }

    public void antennaState(boolean state){
        this.antennaState = state;
    }
}
