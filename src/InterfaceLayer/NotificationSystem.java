package InterfaceLayer;

import HardwareLayer.Notification.LED;
import HeadInterfaces.Updatable;
import TI.BoeBot;

public class NotificationSystem implements Updatable {

    @java.lang.Override
    public void update() {
        BoeBot.rgbShow();

    }

    public void initNotificationSystem(){
        LED led1 = new LED(1);
    }
}
