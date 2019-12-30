package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;
import Utils.Enums.Instructions;
import Utils.Enums.WindDirections;

import java.util.ArrayList;

public class MapSolver {

    /**
     * makes an fresh map with an size of mapWidth by mapHeight
     *
     * @param mapWidth  int which defines the width
     * @param mapHeight int which defines the height
     * @return double array with intersection
     */
    public static Intersection[][] makeMap(int mapWidth, int mapHeight) {
        Intersection[][] intersections = new Intersection[mapWidth][mapHeight];
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                intersections[x][y] = new Intersection();
            }
        }
        return intersections;
    }

    /**
     * this method gets the shortest path to the exit when given a map of intersections using the Dijkstra algorithm
     * @param mapOutOfIntersections double array with intersections
     * @param startingX is the starting coordinate x
     * @param startingY is the starting coordinate y
     * @param endX is the end coordinate x
     * @param endY is the end coordinate y
     * @return instructions how to drive the shortest path
     */
    public static ArrayList<Instructions> solveMap(Intersection[][] mapOutOfIntersections, int startingX, int startingY, int endX, int endY) {
        //initialise some variables
        int tentativeDistance = 0;
        ArrayList<WindDirections> directionsNESW = new ArrayList<>();
        int mapWidth = 0;
        int mapHeight = 0;

        //determines the map width and height
        while (mapOutOfIntersections[mapWidth][0] != null) {
            mapWidth++;
        }
        while (mapOutOfIntersections[0][mapHeight] != null) {
            mapHeight++;
        }

        //set the starting point tentative distance and the end point
        mapOutOfIntersections[startingX][startingY].setTentativeDistance(tentativeDistance);
        mapOutOfIntersections[endX][endY].setTentativeDistance(tentativeDistance);

        //this is where we keep the currentCoordinates coordinates index 0 = x and index 1 = y
        int[] currentCoordinates = new int[2];
        currentCoordinates[0] = startingX;
        currentCoordinates[1] = startingY;

        //while loop that searches for the shortest path
        while (true) {
            tentativeDistance = mapOutOfIntersections[startingX][startingY].getTentativeDistance() + 1;

            //check for all the intersections around the current intersection
            {
                //check intersection to the north
                try {
                    if (!mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].isVisited()) {
                        //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                        if (tentativeDistance < mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].getTentativeDistance())
                            mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].setTentativeDistance(tentativeDistance);
                    }
                } catch (Exception exception) {
                    //this intersection doesnt exist on the map or is blocked
                }

                //check the intersection to the east
                try {
                    if (!mapOutOfIntersections[currentCoordinates[0] + 1][currentCoordinates[1]].isVisited()) {
                        //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                        if (tentativeDistance < mapOutOfIntersections[currentCoordinates[0] + 1][currentCoordinates[1]].getTentativeDistance())
                            mapOutOfIntersections[currentCoordinates[0] + 1][currentCoordinates[1]].setTentativeDistance(tentativeDistance);
                    }
                } catch (Exception exception) {
                    //this intersection doesnt exist on the map or is blocked
                }

                //check intersection to the south
                try {
                    if (!mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].isVisited()) {
                        //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                        if (tentativeDistance < mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].getTentativeDistance())
                            mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].setTentativeDistance(tentativeDistance);
                    }
                } catch (Exception exception) {
                    //this intersection doesnt exist on the map or is blocked
                }

                //check intersection to the west
                try {
                    if (!mapOutOfIntersections[currentCoordinates[0] - 1][currentCoordinates[1]].isVisited()) {
                        //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                        if (tentativeDistance < mapOutOfIntersections[currentCoordinates[0] - 1][currentCoordinates[1]].getTentativeDistance())
                            mapOutOfIntersections[currentCoordinates[0] - 1][currentCoordinates[1]].setTentativeDistance(tentativeDistance);
                    }
                } catch (Exception exception) {
                    //this intersection doesnt exist on the map or is blocked
                }
            }

            //set the current intersection as visited
            mapOutOfIntersections[startingX][startingY].setVisited(true);

            //temperately variables
            int smallestTentativeDistance = Integer.MAX_VALUE;
            int[] nextCoordinates = new int[2];


            //go through all the intersections of the map and gets the smallest tentative distance from the not visited nodes
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    if (!mapOutOfIntersections[x][y].isVisited()) {
                        if (mapOutOfIntersections[x][y].getTentativeDistance() < smallestTentativeDistance) {
                            smallestTentativeDistance = mapOutOfIntersections[x][y].getTentativeDistance();
                            nextCoordinates[0] = x;
                            nextCoordinates[1] = y;
                        }
                    }
                }
            }

            //smallest tentative distance will be the next current intersection
            currentCoordinates = nextCoordinates;

            //the end condition is if the end point has been reached
            if (mapOutOfIntersections[endX][endY].isVisited())
                break;
            //when there is no intersection left we can check we know there is no valid route to the exit
            if (smallestTentativeDistance == Integer.MAX_VALUE)
                return converterFromWindDirectionsToInstructions(directionsNESW);
        }


        //the creating of the shortest path
        {
            //make the variables needed
            currentCoordinates[0] = endX;
            currentCoordinates[1] = endY;

            //while loop with the solving part, here we go from end to start
            while (true) {
                int smallestTentativeDistance = Integer.MAX_VALUE;
                WindDirections gotoDirection = WindDirections.North;

                //check all the directions for the lowest tentative distance
                {
                    //check intersection to the north
                    try {
                        //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
                        if (smallestTentativeDistance > mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].getTentativeDistance()) {
                            smallestTentativeDistance = mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].getTentativeDistance();
                            gotoDirection = WindDirections.North;
                        }
                    } catch (Exception exception) {
                        //this intersection doesnt exist on the map or is blocked
                    }

                    //check the intersection to the east
                    try {
                        //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
                        if (smallestTentativeDistance > mapOutOfIntersections[currentCoordinates[0] + 1][currentCoordinates[1]].getTentativeDistance()) {
                            smallestTentativeDistance = mapOutOfIntersections[currentCoordinates[0] + 1][currentCoordinates[1]].getTentativeDistance();
                            gotoDirection = WindDirections.East;
                        }
                    } catch (Exception exception) {
                        //this intersection doesnt exist on the map or is blocked
                    }

                    //check intersection to the south
                    try {
                        //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
                        if (smallestTentativeDistance > mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].getTentativeDistance()) {
                            smallestTentativeDistance = mapOutOfIntersections[currentCoordinates[0]][currentCoordinates[1] - 1].getTentativeDistance();
                            gotoDirection = WindDirections.South;
                        }
                    } catch (Exception exception) {
                        //this intersection doesnt exist on the map or is blocked
                    }

                    //check intersection to the west
                    try {
                        //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
                        if (smallestTentativeDistance > mapOutOfIntersections[currentCoordinates[0] - 1][currentCoordinates[1]].getTentativeDistance()) {
                            smallestTentativeDistance = mapOutOfIntersections[currentCoordinates[0] - 1][currentCoordinates[1]].getTentativeDistance();
                            gotoDirection = WindDirections.West;
                        }
                    } catch (Exception exception) {
                        //this intersection doesnt exist on the map or is blocked
                    }
                }
                directionsNESW.add(gotoDirection);
                //end condition is when the starting point has been reached
                if (currentCoordinates[0] == startingX && currentCoordinates[1] == startingY) {
                    break;
                }
            }
        }

        return converterFromWindDirectionsToInstructions(directionsNESW);
    }

    /**
     * this method converts NESW directions to instructions
     *
     * @param directionsNESW ArrayList with directions
     * @return ArrayList with Instructions
     */
    public static ArrayList<Instructions> converterFromWindDirectionsToInstructions(ArrayList<WindDirections> directionsNESW) {
        ArrayList<Instructions> instructions = new ArrayList<>();
        WindDirections facingDirection = WindDirections.North;
        for (WindDirections direction : directionsNESW) {
            if (facingDirection == WindDirections.North) {
                if (direction == WindDirections.West) {
                    //turn left
                    instructions.add(Instructions.Left);
                    facingDirection = WindDirections.West;
                    //wait for next intersection
                } else if (direction == WindDirections.North) {
                    //drive forward
                    instructions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.East) {
                    //turn right
                    instructions.add(Instructions.Right);
                    facingDirection = WindDirections.East;
                    //wait for next intersection

                }
            } else if (facingDirection == WindDirections.East) {
                if (direction == WindDirections.East) {
                    //drive forward
                    instructions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.South) {
                    //turn right
                    instructions.add(Instructions.Right);
                    facingDirection = WindDirections.South;
                    //wait for next intersection

                } else if (direction == WindDirections.North) {
                    //turn left
                    instructions.add(Instructions.Left);
                    facingDirection = WindDirections.North;
                    //wait for next intersection

                }
            } else if (facingDirection == WindDirections.South) {
                if (direction == WindDirections.South) {
                    //drive forward
                    instructions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.West) {
                    //turn right
                    instructions.add(Instructions.Right);
                    facingDirection = WindDirections.West;
                    //wait for next intersection

                } else if (direction == WindDirections.East) {
                    //turn left
                    instructions.add(Instructions.Left);
                    facingDirection = WindDirections.East;
                    //wait for next intersection

                }
            } else if (facingDirection == WindDirections.West) {
                if (direction == WindDirections.West) {
                    //drive forward
                    instructions.add(Instructions.Forward);
                    //wait for next intersection

                } else if (direction == WindDirections.North) {
                    //turn right
                    instructions.add(Instructions.Right);
                    facingDirection = WindDirections.North;
                    //wait for next intersection

                } else if (direction == WindDirections.South) {
                    //turn left
                    instructions.add(Instructions.Left);
                    facingDirection = WindDirections.South;
                    //wait for next intersection

                }
            }
        }
        return instructions;
    }


}
