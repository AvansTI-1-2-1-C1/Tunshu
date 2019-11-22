package HardwareLayer;

import TI.BoeBot;
import HeadInterfaces.Updatable;

public class RemoteControl implements Updatable, Switchable {
    private boolean isOn;


    public static int detect(int pin){
        int binaryInput[] = new int[12];
        int pulsenLen = BoeBot.pulseIn(pin,false,6000);
        int buttonNumber = 0;
        // if the puls length is longer then 2000 its the starting bit.

        if (pulsenLen > 2000){
            int lengtes[] = new int[12];
            // fill 12 slots of the array in reversed order.

            for (int i = 11; i >= 0;i--){
                lengtes[i] = BoeBot.pulseIn(pin,false,20000);
            }

            /**
             * turns the digits into 1 and 0 according to the length.
             */
            for (int i = 0; i < 12; i++) {
                if (lengtes[i]<900){
                    binaryInput[i] = 0;
                }else {
                    binaryInput[i] = 1;
                }
            }

            buttonNumber = convertBinary(binaryInput);

        }
        return buttonNumber;
    }

    public static int convertBinary(int[] numbers){
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
