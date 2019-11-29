package HardwareLayer.Navigation;

public interface LineFollowerCallBack {

    //This is the contract that all classes that implement this interface have the following method
    void onLineFollowerStatus(LineFollower lineFollower);
}
