package ApplicationLayer;

import HeadInterfaces.Updatable;
import InterfaceLayer.*;
import InterfaceLayer.Override;
import TI.BoeBot;
import TI.Timer;

import java.util.ArrayList;

public class Tunshu {
    private Drive drive;
    private HitDetection hitDetection;
    private NotificationSystem notificationSystem;
    private InterfaceLayer.Override override;
    private Route route;
    private RouteFollower routeFollower;
    private ArrayList<Updatable> updatable;


    public void start() {
        //initialise every object
        init();


        /**
         * detection loop
         */

//        drive.accelerate(100);
        while (true) {
            Timer hitDetectionTimer = new Timer(50);
            //updates
            for(Updatable updatable: this.updatable){
                updatable.update();
            }

            if(hitDetectionTimer.timeout()){
                hitDetection.update();
                hitDetectionTimer.mark();
            }

            //wait so it is less CPU heavy
            BoeBot.wait(2);

            if(hitDetection.getState()){
                drive.handBreak();
                notificationSystem.setStatus(1);
            } else{
                notificationSystem.setStatus(0);
            }
            //tests
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
        this.override = new Override(this.drive, this.notificationSystem);
        this.route = new Route();
        this.routeFollower = new RouteFollower(drive);
        this.updatable = new ArrayList<>();

        this.updatable.add(notificationSystem);
        this.updatable.add(override);
        this.updatable.add(route);
        this.updatable.add(routeFollower);
    }


}
