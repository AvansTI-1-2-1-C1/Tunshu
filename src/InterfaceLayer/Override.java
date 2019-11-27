package InterfaceLayer;

import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HeadInterfaces.Updatable;


public class Override implements Updatable, RemoteControlCallBack {

    private RemoteControl remoteControl;
    private int selectedButtonCode;
    private Drive drive;
    private NotificationSystem notificationSystem;

    public Override(Drive drive, NotificationSystem notificationSystem) {
        this.remoteControl = new RemoteControl(this);
        this.drive = drive;
        this.notificationSystem = notificationSystem;
    }

    public void useButton() {
        switch (this.selectedButtonCode) {
            case 0:
                break;

            case 1:
                System.out.println("Stop");
                this.drive.handBreak();
                this.drive.setOldSpeed(0);
                this.drive.setSpeed(0);
                this.drive.decelerate(0);
                this.drive.handBreak();
                break;

            case 144:
                // Boebot gaat vooruit
                System.out.println("Vooruit");
                if (this.drive.isBackwards()) {
                    System.out.println("Eerst stop");
                    this.drive.decelerate(0);
                    this.drive.setBackwards(false);
                }
                if (this.drive.isForwards()) {
                    this.drive.accelerate(50);
                }
                this.drive.setForwards(true);
                break;

            case 2192:
                //Boebot gaat achteruit
                System.out.println("Achteruit");
                if (this.drive.isForwards()) {
                    System.out.println("Eerst stop");
                    this.drive.decelerate(0);
                    this.drive.setForwards(false);
                }
                if (this.drive.isBackwards()) {
                    this.drive.accelerate(50);
                }
                this.drive.setBackwards(true);
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
                //Sto
                System.out.println("Fullstop");
                this.drive.setForwards(false);
                this.drive.setForwards(false);
                this.drive.handBreak();
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
                for (int i = 0; i < 2; i++) {
                    this.drive.accelerate(50);
                    this.drive.decelerate(0);
                    this.drive.turnLeft();
                }
                this.drive.decelerate(0);
                break;
        }
    }


    @java.lang.Override
    public void update() {
        remoteControl.update();
        useButton();

    }


    @java.lang.Override
    public void onButtonPress(int buttonPress) {
        this.selectedButtonCode = buttonPress;
        System.out.println(buttonPress);
    }
}
