package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;
import Utils.Enums.Directions;

import java.util.ArrayList;

public class MapSolver {

    /**
     * this method gets the quickest way to the exit when given a map of intersections
     * @param intersections
     * @return insctructions on what to do by an intersection, this can be right, left, forward and back
     */
    public static ArrayList<Directions> mapSolver(Intersection[][] intersections) {
        ArrayList<Character> directionsNESW = new ArrayList<>();
        int x = 20;
        int y = 20;
        boolean exitFound = false;

        while (!exitFound) {
            intersections[x][y].setCounter(intersections[x][y].getCounter() + 5);

            int north = 9;
            int east = 9;
            int south = 9;
            int west = 9;

            if (intersections[x][y + 1] != null) {
                if (intersections[x][y].isNorth()) {
                    north = intersections[x][y + 1].getCounter();
                }
            }
            if (intersections[x + 1][y] != null) {
                if (intersections[x][y].isEast()) {
                    east = intersections[x + 1][y].getCounter();
                }
            }
            if (intersections[x][y - 1] != null) {
                if (intersections[x][y].isSouth()) {
                    south = intersections[x][y - 1].getCounter();
                }
            }
            if (intersections[x - 1][y] != null) {
                if (intersections[x][y].isWest()) {
                    west = intersections[x - 1][y].getCounter();
                }
            }

            if (north < east && north < south && north < west) {
                directionsNESW.add('N');
                y++;
            } else if (east < north && east < south && east < west) {
                directionsNESW.add('E');
                x++;
            } else if (south < north && south < east && south < west) {
                directionsNESW.add('S');
                y--;
            } else if (west < north && west < east && west < south) {
                directionsNESW.add('W');
                x--;
            } else {
                exitFound = true;
            }
        }
        return driveMap(directionsNESW);
    }

    public static ArrayList<Directions> driveMap(ArrayList<Character> directionsNESW) {
        ArrayList<Directions> directions = new ArrayList<>();
        char facingDirection = 'N';
        for (char direction : directionsNESW) {
            if (facingDirection == 'N') {
                if (direction == 'W') {
                    //turn left
                    directions.add(Directions.Left);
                    facingDirection = 'W';
                    //wait for next intersection
                } else if (direction == 'N') {
                    //drive forward
                    directions.add(Directions.Forward);
                    //wait for next intersection

                } else if (direction == 'E') {
                    //turn right
                    directions.add(Directions.Right);
                    facingDirection = 'E';
                    //wait for next intersection

                }
            } else if (facingDirection == 'E') {
                if (direction == 'E') {
                    //drive forward
                    directions.add(Directions.Forward);
                    //wait for next intersection

                } else if (direction == 'S') {
                    //turn right
                    directions.add(Directions.Right);
                    facingDirection = 'S';
                    //wait for next intersection

                } else if (direction == 'N') {
                    //turn left
                    directions.add(Directions.Left);
                    facingDirection = 'N';
                    //wait for next intersection

                }
            } else if (facingDirection == 'S') {
                if (direction == 'S') {
                    //drive forward
                    directions.add("forward");
                    //wait for next intersection

                } else if (direction == 'W') {
                    //turn right
                    directions.add("right");
                    facingDirection = 'W';
                    //wait for next intersection

                } else if (direction == 'E') {
                    //turn left
                    directions.add("left");
                    facingDirection = 'E';
                    //wait for next intersection

                }
            } else if (facingDirection == 'W') {
                if (direction == 'W') {
                    //drive forward
                    directions.add("forward");
                    //wait for next intersection

                } else if (direction == 'N') {
                    //turn right
                    directions.add("right");
                    facingDirection = 'N';
                    //wait for next intersection

                } else if (direction == 'S') {
                    //turn left
                    directions.add("left");
                    facingDirection = 'S';
                    //wait for next intersection

                }
            }
        }
        return directions;
    }


}
