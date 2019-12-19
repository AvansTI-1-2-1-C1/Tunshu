package Utils.CallBacks;

public interface AntennaCallBack {

    //creates a contract that states, if implemented the methods here WILL be part of the class
    //we use this to create callbacks
    void antennaState(boolean state);
}
