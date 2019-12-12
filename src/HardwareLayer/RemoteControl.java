package HardwareLayer;

import TI.BoeBot;
import HeadInterfaces.Updatable;
import InterfaceLayer.NotificationSystem;

public class RemoteControl implements Updatable, Switchable {
    private boolean isOn;
    private RemoteControlCallBack remoteControlCallBack;


    public RemoteControl(RemoteControlCallBack remoteControlCallBack) {
        this.remoteControlCallBack = remoteControlCallBack;
    }

    public void update() {
        int[] binaryInput = new int[12];

        // if the puls length is longer then 2000 its the starting bit.
        if (BoeBot.pulseIn(0, false, 6000) > 2000) {
            int[] lengths = new int[12];

            // fill 12 slots of the array in reversed order.
            for (int i = 11; i >= 0; i--) {
                lengths[i] = BoeBot.pulseIn(0, false, 20000);
            }

            // turns the digits into 1 and 0 according to the length.
            for (int i = 0; i < 12; i++) {
                if (lengths[i] < 900) {
                    binaryInput[i] = 0;
                } else {
                    binaryInput[i] = 1;
                }
            }
        } else if (BoeBot.pulseIn(4, false, 6000) > 2000) {
            int[] lengths = new int[12];

            // fill 12 slots of the array in reversed order.
            for (int i = 11; i >= 0; i--) {
                lengths[i] = BoeBot.pulseIn(4, false, 20000);
            }

            // turns the digits into 1 and 0 according to the length.
            for (int i = 0; i < 12; i++) {
                if (lengths[i] < 900) {
                    binaryInput[i] = 0;
                } else {
                    binaryInput[i] = 1;
                }
            }
        }
        remoteControlCallBack.onButtonPress(convertBinary(binaryInput));
    }


    public static int convertBinary(int[] numbers) {
        int getal = 0;
        for (int i = 0; i < 12; i++) {
            getal += (Math.pow(2, i) * numbers[i]);
        }
        return getal;

    }


    @Override
    public boolean isOn() {
        return isOn;
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
