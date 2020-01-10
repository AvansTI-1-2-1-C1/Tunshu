package Utils.CallBacks;

public interface AntennaCallBack {

    /**
     * creates a contract that states, if implemented the methods here WILL be part of the class
     * we use this to create callbacks
     *
     * @param state will be true if the antenna is touched, else false.
     */
    void antennaState(boolean state);
}
