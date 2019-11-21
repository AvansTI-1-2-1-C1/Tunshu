package HardwareLayer;

public class Motor implements Updatable, MotorCallBack {
    @Override
    public int safetyCheck() {
        return 0;
    }

    @Override
    public void update() {

    }
}
