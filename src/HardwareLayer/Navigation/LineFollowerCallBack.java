package HardwareLayer.Navigation;

public interface LineFollowerCallBack {

    //This is the contract that all classes that implement this interface have the following method
    void onLineFollowerStatus(double leftLineFollowerCallBack, double middleLineFollowerCallBack, double rightLineFollowerCallBack);
}
