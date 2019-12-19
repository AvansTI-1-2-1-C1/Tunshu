package InterfaceLayer;

import HardwareLayer.Navigation.Bluetooth;
import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HardwareLayer.Navigation.BluetoothCallBack;
import HeadInterfaces.Updatable;
import TI.Timer;
import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;
import Utils.Enums.Statuses;


public class Override implements Updatable, RemoteControlCallBack, BluetoothCallBack {

    private RemoteControl remoteControlFront;
    private RemoteControl remoteControlBack;
    private Bluetooth bluetooth;

    private ActiveLineFollower activeLineFollower;

    private DriveCommands selectedCommand;
    private DriveCommands previousCommand;

    private MotorControl motorControl;

    private NotificationSystem notificationSystem;

    private Timer inputDelay;

    private HitDetection hitDetection;

    private RouteFollower routeFollower;

    public Override(MotorControl motorControl, NotificationSystem notificationSystem, HitDetection hitDetection, RouteFollower routeFollower, ActiveLineFollower activeLineFollower) {
        this.remoteControlFront = new RemoteControl(this, 0);
        this.remoteControlBack = new RemoteControl(this, 4);
        this.motorControl = motorControl;
        this.bluetooth = new Bluetooth(this);
        this.notificationSystem = notificationSystem;
        this.hitDetection = hitDetection;
        this.inputDelay = new Timer(250);
        this.routeFollower = routeFollower;
        this.activeLineFollower = activeLineFollower;
        this.selectedCommand = DriveCommands.None;
        this.previousCommand = DriveCommands.None;
    }

    public void useButton() {
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
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(),  -0.8f);
                break;
            case Brake:
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
            case Handbrake:
                //make sure there is nothing detected
                if (!this.hitDetection.getState()) {
                    this.motorControl.setHandBreak(false);
                }
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
        this.remoteControlBack.update();
        this.bluetooth.update();
        useButton();
    }

    @java.lang.Override
    public void onButtonPress(int buttonPress) {
        switch (buttonPress) {
            case 0:
                break;
            case 1:
                System.out.println("brake");
                this.selectedCommand = DriveCommands.Brake;
                break;

            case 144:
                // Boebot gaat vooruit
                System.out.println("forward");
                this.selectedCommand = DriveCommands.Forward;
                break;

            case 2192:
                //Boebot gaat achteruit
                System.out.println("backward");
                this.selectedCommand = DriveCommands.Backward;
                notificationSystem.setStatus(Statuses.Reverse, false);
                break;

            case 3216:
                //Boebot turns left
                System.out.println("left");
                this.selectedCommand = DriveCommands.Left;
                break;

            case 1168:
                //Boebot turns right
                System.out.println("right");
                this.selectedCommand = DriveCommands.Right;
                break;

            case 2704:
                //Stop
                System.out.println("handbreak");
                this.selectedCommand = DriveCommands.Handbrake;
                break;

            case 1936:
                //Faster
                System.out.println("faster");
                this.selectedCommand = DriveCommands.Faster;
                break;

            case 3984:
                //Slower
                System.out.println("slower");
                this.selectedCommand = DriveCommands.Slower;
                break;

            case 656:
                //Mute speaker
                System.out.println("mute");
                this.notificationSystem.mute();
                break;

            case 16:
                if(this.activeLineFollower.isLineFollowerState() && !this.routeFollower.isOn()){
                    this.activeLineFollower.setLineFollowerState(false);
                } else if(!this.activeLineFollower.isLineFollowerState() && !this.routeFollower.isOn()){
                    this.activeLineFollower.setLineFollowerState(true);
                }
                this.selectedCommand = DriveCommands.LineFollower;
                System.out.println("LineFollower");
                break;
            case 2640:
                if(this.routeFollower.isOn()){
                    this.routeFollower.off();
                } else{
                    this.routeFollower.on();
                }
                System.out.println("AB");
                break;
            case 2064:
                //button 2
                System.out.println("Button 2");
                break;
            case 1040:
                //button 3
                System.out.println("Button 3");
            case 3088:
                //button 4
                break;
            case 7480:
                //enter
                break;
            case 464:
                //vierkantje
                break;
            default:
                break;
        }
    }

    @java.lang.Override
    public void onInput(DriveCommands command) {
        this.selectedCommand = command;
    }

    @java.lang.Override
    public String getState(BluetoothStateCommands command) {
        switch (command){
            case Lights:
                if(this.notificationSystem.getLight()){
                    return "t";
                }else {
                    return "f";
                }
            case Sound:
                if(this.notificationSystem.getSound()){
                    return "t";
                }else {
                    return "f";
                }
            case Speed:
                return Float.toString(this.motorControl.getCurrentSpeed());
            default:
                break;
        }
        return null;
    }

    @java.lang.Override
    public void setState(BluetoothStateCommands command, String value) {
        switch (command){
            case Lights:
                if(value.equals("t")){
                    this.notificationSystem.LEDOn();
                }else if(value.equals("f")){
                    this.notificationSystem.ledOff();
                }
                break;
            case Sound:
                if(value.equals("t")){
                    this.notificationSystem.speakerOn();
                }else if(value.equals("f")){
                    this.notificationSystem.speakerOff();
                }
                break;
            case Speed:
                this.motorControl.setMotorsTarget(Float.parseFloat(value), 0f);
                break;
            default:
                break;
        }
    }
}
