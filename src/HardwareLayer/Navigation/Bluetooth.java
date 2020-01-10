package HardwareLayer.Navigation;

import InterfaceLayer.Route;
import Utils.CallBacks.BluetoothCallBack;
import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.SerialConnection;
import Utils.CallBacks.RouteCallBack;
import Utils.Enums.BluetoothStateCommands;
import Utils.Enums.DriveCommands;
import Utils.Enums.Instructions;

import java.util.ArrayList;

public class Bluetooth implements Updatable, Switchable {
    private boolean isOn;
    private SerialConnection serialConnection;
    private BluetoothCallBack bluetoothCallBack;
    private RouteCallBack routeCallBack;

    public Bluetooth(BluetoothCallBack bluetoothCallBack, RouteCallBack routeCallBack) {
        this.serialConnection = new SerialConnection();
        this.bluetoothCallBack = bluetoothCallBack;
        this.routeCallBack = routeCallBack;
        this.isOn = true;
    }


    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * This function checks if there is a new command received via bluetooth
     */
    public void update() {
        if (serialConnection.available() > 0) {
            int amount = (char) serialConnection.readByte();
            int[] buffer = new int[amount];
            for (int i = 0; i < amount - 1; i++) {
                buffer[i] = serialConnection.readByte();
            }

//            serialConnection.writeByte(data);
//            System.out.println("Received Data: " + (char)data);
            switch (buffer[0]) {
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
                    bluetoothCallBack.onInput(DriveCommands.Lock);
                    break;
                //Set speed(o)
                case 111:
                    String number = "";
                    for (int i = 1; i < buffer.length - 1; i++) {
                        number += (char) buffer[i];
                    }
                    if (number.length() > 2) {
                        number = number.charAt(0) + "." + number.substring(1);
                    } else {
                        number = "0." + number;
                    }
                    bluetoothCallBack.setState(BluetoothStateCommands.Speed, number);
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
                    bluetoothCallBack.setState(BluetoothStateCommands.Lights, buffer[1] + "");
                    break;
                //Get Light state(L)
                case 76:
                    char[] answerLight = bluetoothCallBack.getState(BluetoothStateCommands.Lights).toCharArray();
                    serialConnection.writeByte((int) answerLight[0]);
                    break;
                //Set Speaker state(p)
                case 112:
                    bluetoothCallBack.setState(BluetoothStateCommands.Sound, buffer[1] + "");
                    break;
                //Get Speaker state(P)
                case 80:
                    char[] answerSound = bluetoothCallBack.getState(BluetoothStateCommands.Sound).toCharArray();
                    serialConnection.writeByte((int) answerSound[0]);
                    break;
                //Set Route(i)
                case 105:
                    ArrayList<Instructions> route = new ArrayList<>();
                    for (int i = 1; i < buffer.length - 1; i++) {
                        switch ((char) buffer[i]) {
                            case 'w':
                                route.add(Instructions.Forward);
                                break;
                            case 'a':
                                route.add(Instructions.Left);
                                break;
                            case 'd':
                                route.add(Instructions.Right);
                                break;
                            case 's':
                                route.add(Instructions.Stop);
                                break;
                        }
                    }
                    this.routeCallBack.setRoute(route);
                    this.bluetoothCallBack.setState(BluetoothStateCommands.RouteFollower,"t");
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
