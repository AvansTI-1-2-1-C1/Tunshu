package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;
import Utils.Enums.Instructions;
import Utils.Enums.WindDirections;

import java.util.ArrayList;

public class MapSolver {

    /**
     * this method gets the quickest way to the exit when given a map of intersections
     * @param intersections a scan of every intersection
     * @return insctructions on what to do by an intersection, this can be right, left, forward and back
     */
    public static ArrayList<Instructions> mapSolver(Intersection[][] intersections) {
        ArrayList<WindDirections> directionsNESW = new ArrayList<>();
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
                directionsNESW.add(WindDirections.North);
                y++;
            } else if (east < north && east < south && east < west) {
                directionsNESW.add(WindDirections.East);
                x++;
            } else if (south < north && south < east && south < west) {
                directionsNESW.add(WindDirections.South);
                y--;
            } else if (west < north && west < east && west < south) {
                directionsNESW.add(WindDirections.West);
                x--;
            } else {
                exitFound = true;
            }
        }
        return driveMap(directionsNESW);
    }


    public static ArrayList<Instructions> driveMap(ArrayList<WindDirections> directionsNESW) {
        ArrayList<Instructions> directions = new ArrayList<>();
        WindDirections facingDirection = WindDirections.North;
        for (WindDirections direction : directionsNESW) {
            if (facingDirection == WindDirections.North) {
                if (direction == WindDirections.West) {
                    //turn left
                    directions.add(Instructions.Left);
                    facingDirection = WindDirections.West;
                    //wait for next intersection
                } else if (direction == WindDirections.North) {
                    //drive forward
                    directions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.East) {
                    //turn right
                    directions.add(Instructions.Right);
                    facingDirection = WindDirections.East;
                    //wait for next intersection

                }
            } else if (facingDirection == WindDirections.East) {
                if (direction == WindDirections.East) {
                    //drive forward
                    directions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.South) {
                    //turn right
                    directions.add(Instructions.Right);
                    facingDirection = WindDirections.South;
                    //wait for next intersection

                } else if (direction == WindDirections.North) {
                    //turn left
                    directions.add(Instructions.Left);
                    facingDirection = WindDirections.North;
                    //wait for next intersection

                }
            } else if (facingDirection == WindDirections.South) {
                if (direction == WindDirections.South) {
                    //drive forward
                    directions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.West) {
                    //turn right
                    directions.add(Instructions.Right);
                    facingDirection = WindDirections.West;
                    //wait for next intersection

                } else if (direction == WindDirections.East) {
                    //turn left
                    directions.add(Instructions.Left);
                    facingDirection = WindDirections.East;
                    //wait for next intersection

                }
            } else if (facingDirection == WindDirections.West) {
                if (direction == WindDirections.West) {
                    //drive forward
                    directions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.North) {
                    //turn right
                    directions.add(Instructions.Right);
                    facingDirection = WindDirections.North;
                    //wait for next intersection

                } else if (direction == WindDirections.South) {
                    //turn left
                    directions.add(Instructions.Left);
                    facingDirection = WindDirections.South;
                    //wait for next intersection

                }
            }
        }
        return directions;
    }


}
