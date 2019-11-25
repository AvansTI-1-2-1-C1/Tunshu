package HardwareLayer;

import HardwareLayer.Notification.Speaker;
import InterfaceLayer.Drive;
import TI.BoeBot;
import HeadInterfaces.Updatable;
import InterfaceLayer.NotificationSystem;

public class RemoteControl implements Updatable, Switchable {
    private boolean isOn;


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

    public static void useButton(Drive drive, NotificationSystem speaker) {
        switch (detect(0)) {
            case 0:
                break;

            case 1:
                System.out.println("Stop");
                drive.handBreak();
                drive.setOldSpeed(0);
                drive.setSpeed(0);
                drive.decelerate();
                drive.handBreak();
                break;

            case 144:
                System.out.println("Vooruit");
                if (drive.isBackwards()) {
                    System.out.println("Eerst stop");
                    drive.setOldSpeed(drive.getSpeed());
                    drive.setSpeed(0);
                    drive.decelerate();
                    drive.setBackwards(false);
                }
                if (drive.isForwards()) {
                    drive.setOldSpeed(0);
                    drive.setSpeed(50);
                    drive.accelerate();
                }
                drive.setForwards(true);
                break;

            // Boebot gaat vooruit
            case 2192:
                System.out.println("Achteruit");
                if (drive.isForwards()) {
                    System.out.println("Eerst stop");
                    drive.setOldSpeed(drive.getSpeed());
                    drive.setSpeed(0);
                    drive.decelerate();
                    drive.setForwards(false);
                }
                if (drive.isBackwards()) {
                    drive.setSpeed(50);
                    drive.setOldSpeed(0);
                    drive.accelerate();
                }
                drive.setBackwards(true);
                break;

            //Boebot gaat achteruit
            case 3216:
                System.out.println("Links");
                drive.turnLeft();
                break;

            //Boebot gaat naar links
            case 1168:
                System.out.println("Rechts");
                drive.turnRight();
                break;

            //Boebot gaat naar rechts
            case 2704:
                System.out.println("Fullstop");
                drive.setForwards(false);
                drive.setForwards(false);
                drive.handBreak();
                drive.setSpeed(0);
                break;

            case 1936:
                System.out.println("Sneller");
                drive.increaseSpeed();
                break;

            case 3984:
                System.out.println("Langzamer");
                drive.decreaseSpeed();
                break;
            case 656:
                speaker.mute();
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
