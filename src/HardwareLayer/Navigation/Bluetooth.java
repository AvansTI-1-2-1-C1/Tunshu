package HardwareLayer.Navigation;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.SerialConnection;
import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;

import java.util.ArrayList;

public class Bluetooth implements Updatable, Switchable {
    private boolean isOn;
    private SerialConnection serialConnection;
    private BluetoothCallBack bluetoothCallBack;

    public Bluetooth(BluetoothCallBack bluetoothCallBack) {
        this.serialConnection = new SerialConnection();
        this.bluetoothCallBack = bluetoothCallBack;
        this.isOn = true;
    }


    @Override
    public boolean isOn() {
        return isOn;
    }

    public void update() {
        if (serialConnection.available() > 0) {
            int[] data;
            data = new int[3];
            int i = 0;
            while (serialConnection.available() > 0) {
                data[i] = serialConnection.readByte();
                i++;
            }
//            serialConnection.writeByte(data);
//            System.out.println("Received Data: " + (char)data);
            switch (data[0]) {
                //Forward(w)
                case 119:
                    bluetoothCallBack.onInput(DriveCommands.Forward);
                    break;
                //Backwards(s)
                case 115:
                    bluetoothCallBack.onInput(DriveCommands.Backward);
                    break;
                //Left(a)
                case 97:
                    bluetoothCallBack.onInput(DriveCommands.Left);
                    break;
                //Right(d)
                case 100:
                    bluetoothCallBack.onInput(DriveCommands.Right);
                    break;
                //Handbrake(space)
                case 32:
                    bluetoothCallBack.onInput(DriveCommands.Brake);
                    break;
                //Faster(e)
                case 101:
                    bluetoothCallBack.onInput(DriveCommands.Faster);
                    break;
                //Slower(q)
                case 113:
                    bluetoothCallBack.onInput(DriveCommands.Slower);
                    break;
                //Mute(m)
                case 109:
                    bluetoothCallBack.onInput(DriveCommands.Mute);
                    break;
                //LineFollower(r)
                case 114:
                    bluetoothCallBack.onInput(DriveCommands.LineFollower);
                    break;
                //Hand break(h)
                case 104:
                    bluetoothCallBack.onInput(DriveCommands.Handbrake);
                    break;
                //Set speed(o)
                case 111:
                    String speed = "";
                    for (int j = 1; j < 2; j++) {
                        speed += data[j];
                    }
                    bluetoothCallBack.setState(BluetoothStateCommands.Speed, speed);
                    break;
                //Get speed(O)
                case 79:
                    char[] answerSpeed = bluetoothCallBack.getState(BluetoothStateCommands.Speed).toCharArray();
                    for (char ret : answerSpeed) {
                        serialConnection.writeByte(ret);
                    }
                    break;
                //Set Light state(l)
                case 108:
                    bluetoothCallBack.setState(BluetoothStateCommands.Lights, (char)data[1] + "");
                    break;
                //Get Light state(L)
                case 76:
                    char[] answerLight = bluetoothCallBack.getState(BluetoothStateCommands.Lights).toCharArray();
                    serialConnection.writeByte(answerLight[0]);
                    break;
                //Set Speaker state(p)
                case 112:
                    bluetoothCallBack.setState(BluetoothStateCommands.Sound, (char)data[1] + "");
                    break;
                //Get Speaker state(P)
                case 80:
                    char[] answerSound = bluetoothCallBack.getState(BluetoothStateCommands.Sound).toCharArray();
                    serialConnection.writeByte(answerSound[0]);
                    break;
                //All other keys
                default:
                    bluetoothCallBack.onInput(DriveCommands.None);
                    break;
            }
        }


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
