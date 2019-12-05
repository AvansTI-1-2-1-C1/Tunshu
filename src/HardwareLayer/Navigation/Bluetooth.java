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
                    bluetoothCallBack.onInput("forward");
                    break;
                //Backwards(s)
                case 115:
                    bluetoothCallBack.onInput("backward");
                    break;
                //Left(a)
                case 97:
                    bluetoothCallBack.onInput("left");
                    break;
                //Right(d)
                case 100:
                    bluetoothCallBack.onInput("right");
                    break;
                //Handbreak(space)
                case 32:
                    bluetoothCallBack.onInput("brake");
                    break;
                //Faster(e)
                case 101:
                    bluetoothCallBack.onInput("faster");
                    break;
                //Slower(q)
                case 113:
                    bluetoothCallBack.onInput("slower");
                    break;
                //Mute(m)
                case 109:
                    bluetoothCallBack.onInput("mute");
                    break;
                //LineFollower(r)
                case 114:
                    bluetoothCallBack.onInput("LineFollower");
                    break;
                //Hand break(h)
                case 104:
                    bluetoothCallBack.onInput("handbreak");
                    break;
                //All other keys
                default:
                    bluetoothCallBack.onInput("");
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
