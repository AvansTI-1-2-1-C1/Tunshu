package HardwareLayer.Navigation;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.SerialConnection;
import Utils.Enums.DriveCommands;

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
        if (serialConnection.available()>0){
            int data = serialConnection.readByte();
            serialConnection.writeByte(data);
            System.out.println("Received Data: " + (char)data);
            switch (data){
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
