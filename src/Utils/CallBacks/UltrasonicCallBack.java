package Utils.CallBacks;

public interface UltrasonicCallBack {
    //creates a contract that states, if implemented the methods here WILL be part of the class
    //we use this to create callbacks
    void ultrasonicSensorDistance(double distance);
}

