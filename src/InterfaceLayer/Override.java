package InterfaceLayer;

import HardwareLayer.Navigation.Bluetooth;
import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HardwareLayer.Navigation.BluetoothCallBack;
import HeadInterfaces.Updatable;
import TI.Timer;


public class Override implements Updatable, RemoteControlCallBack, BluetoothCallBack {

    private RemoteControl remoteControl;
    private Bluetooth bluetooth;

    private String sellectedCommand;
    private String previousCommand;
    private MotorControl motorControl;
    private NotificationSystem notificationSystem;
    private Timer inputDelay;
    private HitDetection hitDetection;
    private RouteFollower routeFollower;

    public Override(MotorControl motorControl, NotificationSystem notificationSystem, HitDetection hitDetection, RouteFollower routeFollower) {
//        this.remoteControl = new RemoteControl(this);
        this.motorControl = motorControl;
        this.bluetooth = new Bluetooth(this);
        this.notificationSystem = notificationSystem;
        this.hitDetection = hitDetection;
        this.inputDelay = new Timer(250);
        this.routeFollower = routeFollower;
        this.sellectedCommand = "";
        this.previousCommand = "";
    }

    public void useButton() {
        //check if the selected button was pressed right before by checking it against the previous button code and the timer
        if (this.sellectedCommand.equals(this.previousCommand)) {
            if (!this.inputDelay.timeout()) {
                return;
            }
        } else {
            this.previousCommand = this.sellectedCommand;
        }


        //switch statement that selects the corresponding method
        switch (sellectedCommand) {
            case "forward":
                this.motorControl.setMotorsTarget(0.2f, 0f);
                break;
            case "backward":
                this.motorControl.setMotorsTarget(-0.2f, 0f);
                break;
            case "left":
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), motorControl.getCurrentTurnRate() - 0.2f);
                break;
            case "right":
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), motorControl.getCurrentTurnRate() + 0.2f);
                break;
            case "brake":
                this.motorControl.setMotorsTarget(0f, 0f);
                break;
            case "faster":
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed() + 0.2f, 0f);
                break;
            case "slower":
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed() - 0.2f, 0f);
                break;
            case "mute":
                this.notificationSystem.mute();
                break;
            case "LineFollower":
                if (this.routeFollower.isOn()){
                    this.routeFollower.off();
                }else {
                    this.routeFollower.on();
                }
                break;
            case "handbreak":
                //make sure there is nothing detected
                if (!this.hitDetection.getState()){
                    this.motorControl.setHandBreak(false);
                }
                break;
            default:

                break;
        }

        this.inputDelay.mark();
        this.sellectedCommand = "";
    }

    @java.lang.Override
    public void update() {
//        remoteControl.update();
        this.bluetooth.update();
        useButton();

    }

    @java.lang.Override
    public void onButtonPress(int buttonPress) {
        //TODO make something covert to sellectedCommand
    }

    @java.lang.Override
    public void onInput(String command) {
        this.sellectedCommand = command;
    }
}
