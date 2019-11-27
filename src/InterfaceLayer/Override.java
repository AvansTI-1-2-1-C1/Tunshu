package InterfaceLayer;

import HardwareLayer.RemoteControl;
import HardwareLayer.RemoteControlCallBack;
import HeadInterfaces.Updatable;

public class Override implements Updatable, RemoteControlCallBack {

    private RemoteControl remoteControl;
    private int sellectedButtonCode;

    public Override() {
        this.remoteControl = new RemoteControl(this);
    }

    @java.lang.Override
    public void update() {
        remoteControl.update();


    }


    @java.lang.Override
    public void onButtonPress(int buttonPress) {
        this.sellectedButtonCode = buttonPress;
    }
}
