package HardwareLayer;

import HardwareLayer.Notification.Speaker;
import InterfaceLayer.Drive;
import TI.BoeBot;
import HeadInterfaces.Updatable;
import InterfaceLayer.NotificationSystem;

public class RemoteControl implements Updatable, Switchable {
    private boolean isOn;
    private RemoteControlCallBack remoteControlCallBack;
    private Drive drive;
    private NotificationSystem notificationSystem;

    public RemoteControl(RemoteControlCallBack remoteControlCallBack) {
        this.remoteControlCallBack = remoteControlCallBack;
    }
    public RemoteControl(Drive drive, NotificationSystem notificationSystem) {
        this.drive = drive;
        this.notificationSystem = notificationSystem;
    }


    public static int detect(int pin) {
        int binaryInput[] = new int[12];
        int pulsenLen = BoeBot.pulseIn(pin, false, 6000);
        int buttonNumber = 0;
        // if the puls length is longer then 2000 its the starting bit.

        if (pulsenLen > 2000) {
            int lengtes[] = new int[12];
            // fill 12 slots of the array in reversed order.

            for (int i = 11; i >= 0; i--) {
                lengtes[i] = BoeBot.pulseIn(pin, false, 20000);
            }

            /**
             * turns the digits into 1 and 0 according to the length.
             */
            for (int i = 0; i < 12; i++) {
                if (lengtes[i] < 900) {
                    binaryInput[i] = 0;
                } else {
                    binaryInput[i] = 1;
                }
            }

            buttonNumber = convertBinary(binaryInput);

        }
        return buttonNumber;
    }


    public void useButton() {
        switch (detect(0)) {
            case 0:
                break;

            case 1:
                System.out.println("Stop");
                this.drive.handBreak();
                this.drive.setOldSpeed(0);
                this.drive.setSpeed(0);
                this.drive.decelerate();
                this.drive.handBreak();
                break;

            case 144:
                // Boebot gaat vooruit
                System.out.println("Vooruit");
                if (this.drive.isBackwards()) {
                    System.out.println("Eerst stop");
                    this.drive.setOldSpeed(this.drive.getSpeed());
                    this.drive.setSpeed(0);
                    this.drive.decelerate();
                    this.drive.setBackwards(false);
                }
                if (this.drive.isForwards()) {
                    this.drive.setOldSpeed(0);
                    this.drive.setSpeed(50);
                    this.drive.accelerate();
                }
                this.drive.setForwards(true);
                break;

            case 2192:
                //Boebot gaat achteruit
                System.out.println("Achteruit");
                if (this.drive.isForwards()) {
                    System.out.println("Eerst stop");
                    this.drive.setOldSpeed(drive.getSpeed());
                    this.drive.setSpeed(0);
                    this.drive.decelerate();
                    this.drive.setForwards(false);
                }
                if (this.drive.isBackwards()) {
                    this.drive.setSpeed(50);
                    this.drive.setOldSpeed(0);
                    this.drive.accelerate();
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

                    this.drive.setOldSpeed(this.drive.getSpeed());
                    this.drive.setSpeed(50);
                    this.drive.accelerate();

                    this.drive.setOldSpeed(this.drive.getSpeed());
                    this.drive.setSpeed(0);
                    this.drive.decelerate();
                    this.drive.turnLeft();
                }
                this.drive.setOldSpeed(this.drive.getSpeed());
                this.drive.setSpeed(0);
                this.drive.decelerate();
            break;
        }
    }


    public static int convertBinary(int[] numbers) {
        int getal = 0;
        for (int i = 0; i < 12; i++) {
            getal += (Math.pow(2, i) * numbers[i]);
        }
        //System.out.println(getal);
        return getal;

    }


    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {

    }

    @Override
    public void on() {
        isOn = true;
    }

    @Override
    public void off() {
        isOn = false;
    }
}
