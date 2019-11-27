package HardwareLayer.Sensor;

import HardwareLayer.Switchable;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class Antenna implements Updatable, Switchable {

    private boolean isOn;
    private AntennaCallBack antennaCallBack;

    public Antenna(AntennaCallBack antennaCallBack) {
        this.antennaCallBack = antennaCallBack;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void update() {
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
