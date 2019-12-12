package InterfaceLayer;

import HardwareLayer.Navigation.Bluetooth;
import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HardwareLayer.Navigation.BluetoothCallBack;
import HeadInterfaces.Updatable;
import TI.Timer;


public class Override implements Updatable, RemoteControlCallBack, BluetoothCallBack {

    private RemoteControl remoteControlFront;
    private RemoteControl remoteControlBack;
    private Bluetooth bluetooth;

    private String selectedCommand;
    private String previousCommand;
    private MotorControl motorControl;
    private NotificationSystem notificationSystem;
    private Timer inputDelay;
    private HitDetection hitDetection;
    private RouteFollower routeFollower;

    public Override(MotorControl motorControl, NotificationSystem notificationSystem, HitDetection hitDetection, RouteFollower routeFollower) {
        this.remoteControlFront = new RemoteControl(this, 0);
        this.remoteControlBack = new RemoteControl(this, 4);
        this.motorControl = motorControl;
        this.bluetooth = new Bluetooth(this);
        this.notificationSystem = notificationSystem;
        this.hitDetection = hitDetection;
        this.inputDelay = new Timer(250);
        this.routeFollower = routeFollower;
        this.selectedCommand = "";
        this.previousCommand = "";
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
            case "forward":
                this.motorControl.setMotorsTarget(0.2f, 0f);
                break;
            case "backward":
                this.motorControl.setMotorsTarget(-0.2f, 0f);
                break;
            case "left":
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(), 0.8f);
                break;
            case "right":
                this.motorControl.setMotorsTarget(motorControl.getCurrentSpeed(),  -0.8f);
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
                if (this.routeFollower.isOn()) {
                    this.routeFollower.off();
                } else {
                    this.routeFollower.on();
                }
                break;
            case "handbreak":
                //make sure there is nothing detected
                if (!this.hitDetection.getState()) {
                    this.motorControl.setHandBreak(false);
                }
                routeFollower.off();
                break;
            default:

                break;
        }

        this.inputDelay.mark();
        this.selectedCommand = "";
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
                this.selectedCommand = "brake";
                break;

            case 144:
                // Boebot gaat vooruit
                System.out.println("forward");
                this.selectedCommand = "forward";
                break;

            case 2192:
                //Boebot gaat achteruit
                System.out.println("backward");
                this.selectedCommand = "backward";
                notificationSystem.setStatus("reverse", false);
                break;

            case 3216:
                //Boebot turns left
                System.out.println("left");
                this.selectedCommand = "left";
                break;

            case 1168:
                //Boebot turns right
                System.out.println("right");
                this.selectedCommand = "right";
                break;

            case 2704:
                //Stop
                System.out.println("handbreak");
                this.selectedCommand = "handbreak";
                break;

            case 1936:
                //Faster
                System.out.println("faster");
                this.selectedCommand = "faster";
                break;

            case 3984:
                //Slower
                System.out.println("slower");
                this.selectedCommand = "slower";
                break;

            case 656:
                //Mute speaker
                System.out.println("mute");
                this.notificationSystem.mute();
                break;

            case 16:
                //Enable/Disable lineFollower
                System.out.println("LineFollower");
                this.selectedCommand = "LineFollower";
                break;
            case 2640:
                //A-B button
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
    public void onInput(String command) {
        this.selectedCommand = command;
    }
}
