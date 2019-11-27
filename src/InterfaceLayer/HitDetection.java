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

        if(this.ultraSonicDistance < 10){
            hitDetectionState = false;
        }
        if(this.antennaState){
            hitDetectionState = false;
        }
        if(this.ultraSonicDistance > 10 && !this.antennaState){
            hitDetectionState = true;
        }

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
