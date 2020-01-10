package Utils.CallBacks;

public interface UltrasonicCallBack {

    /**
     * this interface will be used to create a callback from the ultrasonic sensor class to the
     * classes that implement this method, like hit detection
     *
     * @param distance this is a double with the value of the distance measured.
     */
    void ultrasonicSensorDistance(double distance);
}

