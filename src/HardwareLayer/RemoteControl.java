package HardwareLayer;

import TI.BoeBot;
import Utils.CallBacks.RemoteControlCallBack;
import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import Utils.Enums.RemoteCommand;

public class RemoteControl implements Updatable, Switchable {

    private boolean isOn;

    private RemoteControlCallBack remoteControlCallBack;

    private int pin;

    public RemoteControl(RemoteControlCallBack remoteControlCallBack, int pin) {
        this.remoteControlCallBack = remoteControlCallBack;
        this.pin = pin;
    }

    /**
     * this method will read pin signals when called
     */
    public void update() {

        int[] binaryInput = new int[12];

        // if the pulse length is longer then 2000 its the starting bit.
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
            }
            buttonPress(convertBinary(binaryInput));
        }
    }

    public void buttonPress(int buttonPress) {
        switch (buttonPress) {
            case 0:
                break;
            case 2704:
                System.out.println("brake");
                remoteControlCallBack.onButtonPress(RemoteCommand.Brake);
                break;

            case 144:
                // Boebot gaat vooruit
                System.out.println("forward");
                remoteControlCallBack.onButtonPress(RemoteCommand.Forward);
                break;

            case 2192:
                //Boebot gaat achteruit
                System.out.println("backward");
                remoteControlCallBack.onButtonPress(RemoteCommand.Backward);
                break;

            case 3216:
                //Boebot turns left
                System.out.println("left");
                remoteControlCallBack.onButtonPress(RemoteCommand.Left);
                break;

            case 1168:
                //Boebot turns right
                System.out.println("right");
                remoteControlCallBack.onButtonPress(RemoteCommand.Right);
                break;

            case 2872:
                //Stop
                System.out.println("handbreak");
                remoteControlCallBack.onButtonPress(RemoteCommand.Handbrake);
                break;

            case 1936:
                //Faster
                System.out.println("faster");
                remoteControlCallBack.onButtonPress(RemoteCommand.Faster);
                break;

            case 3984:
                //Slower
                remoteControlCallBack.onButtonPress(RemoteCommand.Slower);
                break;
            case 656:
                //Mute speaker
                remoteControlCallBack.onButtonPress(RemoteCommand.Mute);
                break;
            case 16:
                //Linefollower
                remoteControlCallBack.onButtonPress(RemoteCommand.LineFollower);
                break;
            case 2640:
                //RouteFollower
                remoteControlCallBack.onButtonPress(RemoteCommand.RouteFollower);
                break;
            case 2064:
                //button 2
                remoteControlCallBack.onButtonPress(RemoteCommand.Button2);
                break;
            case 1040:
                //button 3
                remoteControlCallBack.onButtonPress(RemoteCommand.Button3);
            case 3088:
                //button 4
                remoteControlCallBack.onButtonPress(RemoteCommand.Button4);
                break;
            case 7480:
                //enter
                remoteControlCallBack.onButtonPress(RemoteCommand.Enter);
                break;
            case 464:
                //vierkantje
                remoteControlCallBack.onButtonPress(RemoteCommand.Vierkant);
                break;
            default:
                break;
        }
    }

    /**
     * converts binary array to integer
     *
     * @param numbers the array of 0 and 1
     * @return converted number
     */
    private static int convertBinary(int[] numbers) {
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
