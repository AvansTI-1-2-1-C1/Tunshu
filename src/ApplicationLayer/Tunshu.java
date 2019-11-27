package ApplicationLayer;

import HardwareLayer.Notification.Speaker;
import HardwareLayer.RemoteControl;
import HeadInterfaces.Updatable;
import InterfaceLayer.*;
import InterfaceLayer.Override;
import TI.BoeBot;

import java.util.ArrayList;

public class Tunshu {
    private Drive drive;
    private HitDetection hitDetection;
    private NotificationSystem notificationSystem;
    private InterfaceLayer.Override override;
    private Route route;
    private RouteFollower routeFollower;
    private RemoteControl remoteControl;
    private ArrayList<Updatable> updatables;


    public void start() {
        //initialise every object
        init();


        /**
         * detection loop
         */


        while (true) {
            //updates
            for(Updatable updatable: this.updatables){
                updatable.update();
            }

            //wait so it is less CPU heavy
            BoeBot.wait(2);

            if(hitDetection.getState()){
                drive.decelerate();
            } else{
                drive.accelerate();
            }
            //tests
            remoteControl.useButton();
        }
    }


    /**
     * initialising every object we need
     */
    public void init(){
        /**
         * @todo
         * hitDetection
         * override
         * route
         * routeFollower
         */
        this.drive = new Drive();
        this.hitDetection = new HitDetection();
        this.notificationSystem = new NotificationSystem();
        this.override = new Override();
        this.route = new Route();
        this.routeFollower = new RouteFollower();
        this.remoteControl = new RemoteControl(this.drive, this.notificationSystem);
        this.updatables = new ArrayList<>();
        this.updatables.add(hitDetection);
        this.updatables.add(notificationSystem);
        this.updatables.add(override);
        this.updatables.add(route);
        this.updatables.add(routeFollower);
    }


}
