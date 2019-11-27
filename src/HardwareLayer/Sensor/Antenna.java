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
        if(BoeBot.digitalRead(2)) {
            antennaCallBack.antennaState(BoeBot.digitalRead(2));
        }
        else if(BoeBot.digitalRead(3)){
            antennaCallBack.antennaState(BoeBot.digitalRead(3));
        }
        else{
            antennaCallBack.antennaState(false);
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
