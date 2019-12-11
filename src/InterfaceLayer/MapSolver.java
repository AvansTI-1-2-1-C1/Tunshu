package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;

import java.util.ArrayList;

public class MapSolver {

    public static ArrayList<Character> mapSolver(Intersection[][] intersections) {
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
                if (intersections[x][y + 1].isNorth()) {
                    north = intersections[x][y + 1].getCounter();
                }
            }
            if (intersections[x + 1][y] != null) {
                if (intersections[x + 1][y].isEast()) {
                    east = intersections[x + 1][y].getCounter();
                }
            }
            if (intersections[x][y - 1] != null) {
                if (intersections[x][y - 1].isSouth()) {
                    south = intersections[x][y - 1].getCounter();
                }
            }
            if (intersections[x - 1][y] != null) {
                if (intersections[x - 1][y].isWest()) {
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

    public static ArrayList<Character> driveMap(ArrayList<Character> directionsNESW) {
        ArrayList<Character> directions = new ArrayList<>();
        char facingDirection = 'N';
        for (char direction : directionsNESW) {
            if (facingDirection == 'N') {
                if (direction == 'W') {
                    //turn left
                    directions.add('L');
                    facingDirection = 'W';
                    //wait for next intersection
                } else if (direction == 'N') {
                    //drive forward
                    directions.add('F');
                    //wait for next intersection

                } else if (direction == 'E') {
                    //turn right
                    directions.add('R');
                    facingDirection = 'E';
                    //wait for next intersection

                }
            } else if (facingDirection == 'E') {
                if (direction == 'E') {
                    //drive forward
                    directions.add('F');
                    //wait for next intersection

                } else if (direction == 'S') {
                    //turn right
                    directions.add('R');
                    facingDirection = 'S';
                    //wait for next intersection

                } else if (direction == 'N') {
                    //turn left
                    directions.add('L');
                    facingDirection = 'N';
                    //wait for next intersection

                }
            } else if (facingDirection == 'S') {
                if (direction == 'S') {
                    //drive forward
                    directions.add('F');
                    //wait for next intersection

                } else if (direction == 'W') {
                    //turn right
                    directions.add('R');
                    facingDirection = 'W';
                    //wait for next intersection

                } else if (direction == 'E') {
                    //turn left
                    directions.add('L');
                    facingDirection = 'E';
                    //wait for next intersection

                }
            } else if (facingDirection == 'W') {
                if (direction == 'W') {
                    //drive forward
                    directions.add('F');
                    //wait for next intersection

                } else if (direction == 'N') {
                    //turn right
                    directions.add('R');
                    facingDirection = 'N';
                    //wait for next intersection

                } else if (direction == 'S') {
                    //turn left
                    directions.add('L');
                    facingDirection = 'S';
                    //wait for next intersection

                }
            }
        }
        return directions;
    }


}
