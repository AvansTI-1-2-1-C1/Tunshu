package HardwareLayer.Navigation;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.SerialConnection;

public class Bluetooth implements Updatable, Switchable {
    private boolean isOn;
private SerialConnection serialConnection;

    public Bluetooth() {
        this.serialConnection = new SerialConnection();
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
            System.out.println("Received Data");
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
