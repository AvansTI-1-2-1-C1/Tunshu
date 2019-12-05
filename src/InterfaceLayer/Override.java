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
        if (sellectedCommand.equals(previousCommand)) {
            if (!inputDelay.timeout()) {
                return;
            }
        } else {
            previousCommand = sellectedCommand;
        }



        //switch statement that selects the corresponding method
        switch (sellectedCommand) {
            case "forward":
                motorControl.setMotorsTarget(0.2f, 0f);
                break;
            case "backward":
                motorControl.setMotorsTarget(-0.2f, 0f);
                break;
            case "left":
                motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), -1f);
                break;
            case "right":
                motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), 1f);
                break;
            case "brake":
                motorControl.setMotorsTarget(0f, 0f);
                break;
            case "faster":
                motorControl.setMotorsTarget(motorControl.getCurrentSpeed() + 0.2f, 0f);
                break;
            case "slower":
                motorControl.setMotorsTarget(motorControl.getCurrentSpeed() - 0.2f, 0f);
                break;
            case "mute":
                notificationSystem.mute();
                break;
            case "LineFollower":
                this.routeFollower.turnOn();
                break;
        }

        inputDelay.mark();
        this.sellectedCommand = "";
    }

    @java.lang.Override
    public void update() {
//        remoteControl.update();
        bluetooth.update();
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
