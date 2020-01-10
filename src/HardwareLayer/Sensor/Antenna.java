package HardwareLayer.Sensor;

import Utils.CallBacks.AntennaCallBack;
import InterfaceLayer.HeadInterfaces.Switchable;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.BoeBot;

public class Antenna implements Updatable, Switchable {

    private boolean isOn;
    private AntennaCallBack antennaCallBack;

    public Antenna(AntennaCallBack antennaCallBack) {
        //the callback interface is routed to one of the attributes
        this.antennaCallBack = antennaCallBack;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    /**
     * the update method will be constantly called, this is to ensure the bot does not react too late
     */
    @Override
    public void update() {

        //the antenna state is declared by reading the input pins,
        //we chose to make no difference between left and right antenna
        antennaCallBack.antennaState(!BoeBot.digitalRead(2) || !BoeBot.digitalRead(3));
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
