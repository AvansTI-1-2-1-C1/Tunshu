package InterfaceLayer;

import HardwareLayer.Navigation.LineFollower;
import Utils.CallBacks.LineFollowerCallBack;
import InterfaceLayer.HeadInterfaces.Updatable;
import Utils.Enums.LineFollowerValue;
import TI.Timer;

import java.util.ArrayList;

public class ActiveLineFollower implements Updatable, LineFollowerCallBack {

    //the three line follower sensors
    private LineFollowerValue middleSensorStatus;
    private LineFollowerValue rightSensorStatus;
    private LineFollowerValue leftSensorStatus;

    private ArrayList<LineFollower> lineFollowerList;

    private MotorControl motorControl;

    private Timer performanceDelayTimer;

    private boolean lineFollowerState;

    public ActiveLineFollower(MotorControl motorControl) {

        this.motorControl = motorControl;

        this.lineFollowerList = new ArrayList<>();

        this.performanceDelayTimer = new Timer(50);

        this.lineFollowerState = true;

        //Here the Line follower is created, this class stands for all three the sensors,
        //we chose this option to make the callbacks and updates more easy and line efficient
        this.lineFollowerList = new ArrayList<>();
        this.lineFollowerList.add(new LineFollower("leftSensor", 2, this));
        this.lineFollowerList.add(new LineFollower("middleSensor", 1, this));
        this.lineFollowerList.add(new LineFollower("rightSensor", 0, this));
    }

    /**
     * if the line follower state boolean is true, the update method will update the
     * the line followers and wil steer left and right to correct the misplacement
     * from the line
     */
    public void update() {

        //this will be used to turn the line follower on and off
        if (!this.lineFollowerState)
            return;

        //for each updatable line follower to update all of the line followers
        for (LineFollower lineFollower : lineFollowerList) {
            lineFollower.update();
        }

        motorControl.setSlowAccelerate(false);

        if (performanceDelayTimer.timeout()) {
            //if the middle sensor is misplaced the correcting will start
            if (this.middleSensorStatus == LineFollowerValue.White) {

                //If the right sensor detects a line it steers left
                if (this.rightSensorStatus == LineFollowerValue.Black) {
                    this.motorControl.setMotorsTarget(0.2f, 1f);
                }

                //If the left sensor detects a line it steers right
                if (this.leftSensorStatus == LineFollowerValue.Black) {
                    this.motorControl.setMotorsTarget(0.2f, -1f);
                }

            } else if (this.middleSensorStatus == LineFollowerValue.Black) {
                this.motorControl.setMotorsTarget(0.4f, 0);
            }

            this.performanceDelayTimer.mark();
        }

    }

    public boolean isLineFollowerState() {
        return lineFollowerState;
    }

    public void setLineFollowerState(boolean lineFollowerState) {
        this.lineFollowerState = lineFollowerState;
    }

    /**
     * callback from the line followers to update the line position
     *
     * @param lineFollower gives himself back
     */
    public void onLineFollowerStatus(LineFollower lineFollower) {
        switch (lineFollower.getSensorName()) {

            case "leftSensor":
                this.leftSensorStatus = lineFollower.getDetectedColor();
                break;
            case "middleSensor":
                this.middleSensorStatus = lineFollower.getDetectedColor();
                break;
            case "rightSensor":
                this.rightSensorStatus = lineFollower.getDetectedColor();
                break;
        }
    }
}
