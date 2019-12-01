package HardwareLayer.Navigation;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.SerialConnection;

public class Bluetooth implements Updatable, Switchable {
    private boolean isOn;
    private SerialConnection serialConnection;
    private BluetoothCallBack bluetoothCallBack;

    public Bluetooth(BluetoothCallBack bluetoothCallBack) {
        this.serialConnection = new SerialConnection();
        this.bluetoothCallBack = bluetoothCallBack;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {
        if (serialConnection.available()>0){
            int data = serialConnection.readByte();
            serialConnection.writeByte(data);
            System.out.println("Received Data: " + (char)data);
            switch (data){
                //Forward(w)
                case 119:
                    bluetoothCallBack.onInput(144);
                    break;
                //Backwards(s)
                case 115:
                    bluetoothCallBack.onInput(2192);
                    break;
                //Left(a)
                case 97:
                    bluetoothCallBack.onInput(3216);
                    break;
                //Right(d)
                case 100:
                    bluetoothCallBack.onInput(1168);
                    break;
                //Handbreak(space)
                case 32:
                    bluetoothCallBack.onInput(2704);
                    break;
                //Faster(e)
                case 101:
                    bluetoothCallBack.onInput(1936);
                    break;
                //Slower(q)
                case 113:
                    bluetoothCallBack.onInput(3984);
                    break;
                //Mute(m)
                case 109:
                    bluetoothCallBack.onInput(656);
                    break;
                //LineFollower(r)
                case 114:
                    bluetoothCallBack.onInput(16);
                    break;
                //All other keys
                default:
                    bluetoothCallBack.onInput(0);
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
