package InterfaceLayer;

import HeadInterfaces.Updatable;

import java.util.ArrayList;

public class Route implements Updatable {

    public void driveMap(ArrayList<Character> directions) {
        facingDirection = 'N';
        for (char direction : directions) {
            if (facingDirection == 'N') {
                if (direction == 'W') {
                    //turn left
                    routeFollower.turnLeft();
                    facingDirection = 'W';
                    //wait for next intersection
                } else if (direction == 'N') {
                    //drive forward
                    routeFollower.goForward();
                    //wait for next intersection

                } else if (direction == 'E') {
                    //turn right
                    routeFollower.turnRight();
                    facingDirection = 'E';
                    //wait for next intersection

                }
            } else if (facingDirection == 'E') {
                if (direction == 'E') {
                    //drive forward
                    routeFollower.goForward();
                    //wait for next intersection

                } else if (direction == 'S') {
                    //turn right
                    routeFollower.turnRight();
                    facingDirection = 'S';
                    //wait for next intersection

                } else if (direction == 'N') {
                    //turn left
                    routeFollower.turnLeft();
                    facingDirection = 'N';
                    //wait for next intersection

                }
            } else if (facingDirection == 'S') {
                if (direction == 'S') {
                    //drive forward
                    routeFollower.goForward();
                    //wait for next intersection

                } else if (direction == 'W') {
                    //turn right
                    routeFollower.turnRight();
                    facingDirection = 'W';
                    //wait for next intersection

                } else if (direction == 'E') {
                    //turn left
                    routeFollower.turnLeft();
                    facingDirection = 'E';
                    //wait for next intersection

                }
            } else if (facingDirection == 'W') {
                if (direction == 'W') {
                    //drive forward
                    routeFollower.goForward();
                    //wait for next intersection

                } else if (direction == 'N') {
                    //turn right
                    routeFollower.turnRight();
                    facingDirection = 'N';
                    //wait for next intersection

                } else if (direction == 'S') {
                    //turn left
                    routeFollower.turnLeft();
                    facingDirection = 'S';
                    //wait for next intersection

                }
            }
        }
    @java.lang.Override
    public void update() {

    }
}
