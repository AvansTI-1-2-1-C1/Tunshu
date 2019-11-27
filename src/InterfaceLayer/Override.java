package InterfaceLayer;

import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HeadInterfaces.Updatable;
import TI.BoeBot;

import static HardwareLayer.RemoteControl.convertBinary;


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
    public void updateIn() {

        int pin = 0;
        int binaryInput[] = new int[12];
        int pulseLen = BoeBot.pulseIn(pin, false, 6000);

        // if the pulse length is longer then 2000 its the starting bit.
        if (pulseLen > 2000) {
            int lengths[] = new int[12];
            // fill 12 slots of the array in reversed order.

            for (int i = 11; i >= 0; i--) {
                lengths[i] = BoeBot.pulseIn(pin, false, 20000);
            }

            /**
             * turns the digits into 1 and 0 according to the length.
             */
            for (int i = 0; i < 12; i++) {
                if (lengths[i] < 900) {
                    binaryInput[i] = 0;
                } else {
                    binaryInput[i] = 1;
                }
            }
            this.selectedButtonCode = convertBinary(binaryInput);
        }
    }

    public void useButton() {
        switch (this.selectedButtonCode) {
            case 0:
                break;

            case 1:
                System.out.println("Stop");
                this.drive.handbrake();
                this.drive.setOldSpeed(0);
                this.drive.setSpeed(0);
                this.drive.decelerate(0);
                this.drive.handbrake();
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
                this.drive.cirkel();
                break;
        }
        this.selectedButtonCode = 0;
    }

    @java.lang.Override
    public void update() {
        updateIn();
        useButton();

    }


    @java.lang.Override
    public void onButtonPress(int buttonPress) {
        this.selectedButtonCode = buttonPress;
        System.out.println(buttonPress);
    }
}
