package InterfaceLayer;

import HeadInterfaces.Updatable;

import java.util.ArrayList;

public class Route implements Updatable {

    private ArrayList<Character> directions;
    private int instructionCounter;

    public Route() {
        this.instructionCounter = 0;
    }

    public ArrayList<Character> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<Character> directions) {
        this.directions = directions;
    }

    /*public void driveMap() {
        char facingDirection = 'N';
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
    }*/

    public Character getDirection(){
        char direction = directions.get(instructionCounter);
        this.instructionCounter = this.instructionCounter+1;
        return direction;
    }

    @java.lang.Override
    public void update() {

    }
}
