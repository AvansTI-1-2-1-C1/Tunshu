package InterfaceLayer;

import HardwareLayer.Sensor.Antenna;
import Utils.CallBacks.AntennaCallBack;
import HardwareLayer.Sensor.Ultrasonic;
import Utils.CallBacks.UltrasonicCallBack;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.Timer;

public class HitDetection implements Updatable, UltrasonicCallBack, AntennaCallBack {


    private Ultrasonic ultrasonic;
    private Antenna antenna;

    private double ultraSonicDistance;
    private boolean antennaState;
    private Timer detectTimer;
    private Timer hitDetectStateTimer;

    private Override override;
    private MotorControl motorControl;

    private boolean isDetectingObject;


    public HitDetection(Override override, MotorControl motorControl) {

        //the ultrasonic and the antenna are initialized with the callback as parameter
        this.ultrasonic = new Ultrasonic(this);
        this.antenna = new Antenna(this);
        this.override = override;
        this.detectTimer = new Timer(10);
        this.hitDetectStateTimer = new Timer(250);
        this.motorControl = motorControl;
    }


    /**
     * this is the default update function this will iterate every loop
     */
    @java.lang.Override
    public void update() {
        if (detectTimer.timeout()) {
            //the sensors are updated here, doing this will change the attributes in this class to use them afterwards
            // for more detail, read the comments in both these classes
            ultrasonic.update();
            antenna.update();
            detectTimer.mark();

            if ((this.ultraSonicDistance < 20 && this.ultraSonicDistance > 0) || this.antennaState) {
                isDetectingObject = true;
                override.setLocked(true);
                motorControl.setLocked(true);
                motorControl.setTurning(false);
                motorControl.stop();
                hitDetectStateTimer.mark();
            } else {
                if (hitDetectStateTimer.timeout()) {
                    isDetectingObject = false;
                    motorControl.setLocked(false);
                }
            }
        }


    }

    /**
     * getter for the hit detection state
     *
     * @return boolean true when the sensors detect an object too close
     */
    public boolean isDetectingObject() {

        //this method returns the state of the hit detection currently if true there is a detect
        return isDetectingObject;

    }

    /**
     * this is the callback when the ultrasonic gets updated.
     *
     * @param distance this parameter will set the attribute in the class
     */
    public void ultrasonicSensorDistance(double distance) {

        //This is one of the callbacks from the sensors, both of these callbacks call a method from the sensor
        //to communicate with the hit detection class
        this.ultraSonicDistance = distance;

    }

    /**
     * this is the callback when the antenna class gets updated
     *
     * @param state this parameter will set the attribute in the class
     */
    public void antennaState(boolean state) {
        //This is one of the callbacks from the sensors, both of these callbacks call a method from the sensor
        //to communicate with the hit detection class
        this.antennaState = state;

    }
}
