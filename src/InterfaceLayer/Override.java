package InterfaceLayer;

import ApplicationLayer.Tunshu;
import HardwareLayer.Navigation.Bluetooth;
import HardwareLayer.RemoteControl;
import Utils.CallBacks.RemoteControlCallBack;
import Utils.CallBacks.BluetoothCallBack;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.Timer;
import Utils.CallBacks.RouteCallBack;
import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;
import Utils.Enums.RemoteCommand;


public class Override implements Updatable, RemoteControlCallBack, BluetoothCallBack {

    private RemoteControl remoteControlFront;
    private Bluetooth bluetooth;

    private ActiveLineFollower activeLineFollower;

    private DriveCommands selectedCommand;
    private DriveCommands previousCommand;

    private MotorControl motorControl;

    private NotificationSystem notificationSystem;

    private Timer inputDelay;

    private Route route;

    private RouteFollower routeFollower;
    private boolean isLocked;

    public Override(MotorControl motorControl, NotificationSystem notificationSystem, RouteFollower routeFollower, ActiveLineFollower activeLineFollower, RouteCallBack routeCallBack, Route route) {
        this.remoteControlFront = new RemoteControl(this, 0);
        this.motorControl = motorControl;
        this.bluetooth = new Bluetooth(this, routeCallBack);
        this.notificationSystem = notificationSystem;
        this.inputDelay = new Timer(250);
        this.routeFollower = routeFollower;
        this.activeLineFollower = activeLineFollower;
        this.selectedCommand = DriveCommands.None;
        this.previousCommand = DriveCommands.None;
        this.isLocked = false;
        this.route = route;
    }

    /**
     * this function uses the selected command and calls the corresponding methods
     */
    private void useButton() {
        //check if the selected button was pressed right before by checking it against the previous button code and the timer
        if (this.selectedCommand.equals(this.previousCommand)) {
            if (!this.inputDelay.timeout()) {
                return;
            }
        } else {
            this.previousCommand = this.selectedCommand;
        }

        //switch statement that selects the corresponding method
        switch (this.selectedCommand) {
            case Forward:
                this.motorControl.setMotorsTarget(0.2f, 0f);
                break;
            case Backward:
                this.motorControl.setMotorsTarget(-0.2f, 0f);
                break;
            case Left:
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), 0.8f);
                break;
            case Right:
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), -0.8f);
                break;
            case Brake:
                routeFollower.setTurning(false);
                motorControl.setTurning(false);
                this.routeFollower.off();
                this.motorControl.setMotorsTarget(0f, 0f);
                break;
            case Faster:
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed() + 0.2f, 0f);
                break;
            case Slower:
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed() - 0.2f, 0f);
                break;
            case Mute:
                this.notificationSystem.mute();
                break;
            case LineFollower:
                break;
            case Lock:
                isLocked = !isLocked;
                motorControl.setLocked(false);
                break;
            default:
                break;
        }

        this.inputDelay.mark();
        this.selectedCommand = DriveCommands.None;
    }

    @java.lang.Override
    public void update() {
        this.remoteControlFront.update();
        this.bluetooth.update();
        useButton();
    }

    /**
     * this is a callback to set the selected command to the given command
     *
     * @param command is a Enum list of available commands
     */
    @java.lang.Override
    public void onButtonPress(RemoteCommand command) {
        switch (command) {
            case None:
                break;
            case Brake:
                System.out.println("brake");
                this.selectedCommand = DriveCommands.Brake;
                break;

            case Forward:
                // Boebot gaat vooruit
                System.out.println("forward");
                this.selectedCommand = DriveCommands.Forward;
                break;

            case Backward:
                //Boebot gaat achteruit
                System.out.println("backward");
                this.selectedCommand = DriveCommands.Backward;
                break;

            case Left:
                //Boebot turns left
                System.out.println("left");
                this.selectedCommand = DriveCommands.Left;
                break;

            case Right:
                //Boebot turns right
                System.out.println("right");
                this.selectedCommand = DriveCommands.Right;
                break;

            case Handbrake:
                //lock
                System.out.println("lock");
                this.selectedCommand = DriveCommands.Lock;
                motorControl.setLocked(isLocked);
                break;

            case Faster:
                //Faster
                System.out.println("faster");
                this.selectedCommand = DriveCommands.Faster;
                break;

            case Slower:
                //Slower
                System.out.println("slower");
                this.selectedCommand = DriveCommands.Slower;
                break;

            case Mute:
                //Mute speaker
                System.out.println("mute");
                this.notificationSystem.mute();
                break;

            case LineFollower:
                if (this.activeLineFollower.isLineFollowerState() && !this.routeFollower.isOn()) {
                    this.activeLineFollower.setLineFollowerState(false);
                } else if (!this.activeLineFollower.isLineFollowerState() && !this.routeFollower.isOn()) {
                    this.activeLineFollower.setLineFollowerState(true);
                }
                this.selectedCommand = DriveCommands.LineFollower;
                System.out.println("LineFollower");
                break;
            case RouteFollower:
                if (this.routeFollower.isOn()) {
                    this.routeFollower.off();
                } else {
                    this.routeFollower.on();
                }
                System.out.println("AB");
                break;
            case Button2:
                //button 2
                System.out.println("Button 2");
                break;
            case Button3:
                //button 3
                System.out.println("Button 3");
            case Button4:
                //button 4
                System.out.println("Button 4");
                break;
            case Enter:
                //enter
                this.route.clear();
                System.out.println("Enter button");
                break;
            case Vierkant:
                //vierkantje
                System.out.println("Vierkant button");
                break;
            default:
                break;
        }
    }

    /**
     * callback for setting the selected command
     *
     * @param command is a Enum list of available commands
     */
    @java.lang.Override
    public void onInput(DriveCommands command) {
        this.selectedCommand = command;
    }

    /**
     * get the state of the notification system
     *
     * @param command is a Enum list of available commands
     * @return what the state of the override is
     */
    @java.lang.Override
    public String getState(BluetoothStateCommands command) {
        switch (command) {
            case Lights:
                if (this.notificationSystem.getLight()) {
                    return "t";
                } else {
                    return "f";
                }
            case Sound:
                if (this.notificationSystem.getSound()) {
                    return "t";
                } else {
                    return "f";
                }
            case Speed:
                return Float.toString(this.motorControl.getCurrentSpeed());
            default:
                break;
        }
        return null;
    }

    /**
     * a simple setter for notification system with the given command
     *
     * @param command is a Enum list of available commands
     * @param value is the value of the LineFollowers
     */
    @java.lang.Override
    public void setState(BluetoothStateCommands command, String value) {
        switch (command) {
            case Lights:
                if (value.equals("t")) {
                    this.notificationSystem.LEDOn();
                } else if (value.equals("f")) {
                    this.notificationSystem.LEDOff();
                }
                break;
            case Sound:
                if (value.equals("t")) {
                    this.notificationSystem.speakerOn();
                } else if (value.equals("f")) {
                    this.notificationSystem.speakerOff();
                }
                break;
            case Speed:
                this.motorControl.setMotorsTarget(Float.parseFloat(value), 0f);
                break;
            case RouteFollower:
                if(value.equals("t")){
                    this.routeFollower.on();
                }else if(value.equals("f")){
                    this.routeFollower.off();
                }
                break;
            default:
                break;
        }
    }

    /**
     * simple getter for isUnlocked
     *
     * @return isUnlocked boolean
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * simple setter for isLocked
     *
     * @param isLocked is to what isLocked need to be set
     */
    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
