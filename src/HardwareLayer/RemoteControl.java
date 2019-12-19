package HardwareLayer;

import TI.BoeBot;
import Utils.CallBacks.RemoteControlCallBack;
import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;

public class RemoteControl implements Updatable, Switchable {
    private boolean isOn;
    private RemoteControlCallBack remoteControlCallBack;
    private int pin;

    public RemoteControl(RemoteControlCallBack remoteControlCallBack, int pin) {
        this.remoteControlCallBack = remoteControlCallBack;
        this.pin = pin;
    }

    public void update() {
        int[] binaryInput = new int[12];

        // if the puls length is longer then 2000 its the starting bit.
        if (BoeBot.pulseIn(this.pin, false, 6000) > 2000) {
            int[] lengths = new int[12];

            // fill 12 slots of the array in reversed order.
            for (int i = 11; i >= 0; i--) {
                lengths[i] = BoeBot.pulseIn(this.pin, false, 20000);
            }

            // turns the digits into 1 and 0 according to the length.
            for (int i = 0; i < 12; i++) {
                if (lengths[i] < 900) {
                    binaryInput[i] = 0;
                } else {
                    binaryInput[i] = 1;
                }
                remoteControlCallBack.onButtonPress(convertBinary(binaryInput));
            }
        }
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
