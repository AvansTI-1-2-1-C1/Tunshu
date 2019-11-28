package InterfaceLayer;

import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HeadInterfaces.Updatable;
import TI.Timer;



public class Override implements Updatable, RemoteControlCallBack {

    private RemoteControl remoteControl;
    private int selectedButtonCode;
    private Drive drive;
    private NotificationSystem notificationSystem;
    private int previousButtonCode;
    private Timer inputDelay;
    private RouteFollower routeFollower;

    public Override(Drive drive, NotificationSystem notificationSystem, RouteFollower routeFollower) {
        this.remoteControl = new RemoteControl(this);
        this.drive = drive;
        this.notificationSystem = notificationSystem;
        this.inputDelay = new Timer(500);
        this.routeFollower = routeFollower;
    }

    public void useButton() {
        //check if the selected button was pressed right before by checking it against the previous button code and the timer
        if (selectedButtonCode == previousButtonCode) {
            if (!inputDelay.timeout()) {
                return;
            }
        } else {
            previousButtonCode = selectedButtonCode;
        }

        //switch statement that selects the corresponding method
        switch (this.selectedButtonCode) {
            case 0:
                break;

            case 1:
                System.out.println("Stop");
                this.routeFollower.turnOff();
                this.drive.handbrake();
                this.drive.setOldSpeed(0);
                this.drive.setSpeed(0);
                this.drive.decelerate(0);
                this.drive.handbrake();
                this.drive.setForwards(true);
                this.drive.setBackwards(false);
                break;

            case 144:
                // Boebot gaat vooruit
                System.out.println("Vooruit");
                if (this.drive.isBackwards()) {
                    System.out.println("Eerst stop");
                    this.drive.decelerate(0);
                    this.drive.setBackwards(false);
                    this.drive.setForwards(true);
                } else if (this.drive.isForwards()) {
                    this.drive.accelerate(50);
                }
                break;

            case 2192:
                //Boebot gaat achteruit
                System.out.println("Reverse");
                if (this.drive.isForwards()) {
                    System.out.println("First stop");
                    this.drive.decelerate(0);
                    this.drive.setForwards(false);
                    this.drive.setBackwards(true);
                } else if (this.drive.isBackwards()) {
                    this.drive.accelerate(50);
                }
                notificationSystem.setStatus("reverse");
                break;

            case 3216:
                //Boebot turns left
                System.out.println("Links");
                this.drive.turnLeft();
                break;

            case 1168:
                //Boebot turns right
                System.out.println("Rechts");
                this.drive.turnRight();
                break;

            case 2704:
                //Stop
                System.out.println("Fullstop");
                this.drive.setForwards(true);
                this.drive.setBackwards(false);
                this.drive.handbrake();
                this.drive.setSpeed(0);
                break;

            case 1936:
                System.out.println("Sneller");
                this.drive.increaseSpeed();
                break;

            case 3984:
                System.out.println("Langzamer");
                this.drive.decreaseSpeed();
                break;
            case 656:
                System.out.println("mute");
                this.notificationSystem.mute();
                break;
            case 16:
                this.routeFollower.turnOn();
                break;

            default:
                break;
        }
        inputDelay.mark();
        this.selectedButtonCode = 0;
    }

    @java.lang.Override
    public void update() {
        remoteControl.update();
        useButton();

    }


    @java.lang.Override
    public void onButtonPress(int buttonPress) {
        this.selectedButtonCode = buttonPress;
}
}
