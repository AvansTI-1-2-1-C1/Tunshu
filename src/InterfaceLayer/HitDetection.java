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

        //the ultrasonic and the antenna are initialized with the callback as parameter
        this.ultrasonic = new Ultrasonic(this);
        this.antenna = new Antenna(this);

    }

    @java.lang.Override
    //this is the default update function this will iterate every loop
    public void update() {

        //the sensors are updated here, doing this will change the attributes in this class to use them afterwards
        // for more detail, read the comments in both these classes
        ultrasonic.update();
        antenna.update();

        //Depending on our given attributes the hit detection will change its state
        hitDetectionState = (this.ultraSonicDistance < 20 && this.ultraSonicDistance > 0) || this.antennaState;

    }

    public boolean getState(){

        //this method returns the state of the hit detection currently if true there is a detect
        return hitDetectionState;

    }

    public void ultrasonicSensorDistance(double distance){

        //This is one of the callbacks from the sensors, both of these callbacks call a method from the sensor
        //to communicate with the hit detection class
        this.ultraSonicDistance = distance;

    }

    public void antennaState(boolean state){
        //This is one of the callbacks from the sensors, both of these callbacks call a method from the sensor
        //to communicate with the hit detection class
        this.antennaState = state;

    }
}
