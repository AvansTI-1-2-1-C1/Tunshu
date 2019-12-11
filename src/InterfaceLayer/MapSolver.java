package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;

import java.util.ArrayList;

public class MapSolver {

    public static ArrayList<Character> mapSolver(Intersection[][] intersections) {
        ArrayList<Character> directions = new ArrayList<>();
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
                directions.add('N');
                y++;
            } else if (east < north && east < south && east < west) {
                directions.add('E');
                x++;
            } else if (south < north && south < east && south < west) {
                directions.add('S');
                y--;
            } else if (west < north && west < east && west < south) {
                directions.add('W');
                x--;
            } else {
                exitFound = true;
            }
        }

        return directions;
    }


}
