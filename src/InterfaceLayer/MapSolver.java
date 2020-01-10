package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;
import InterfaceLayer.HeadInterfaces.Updatable;
import Utils.Enums.Instructions;
import Utils.Enums.MapSolverState;
import Utils.Enums.WindDirections;

import java.util.ArrayList;


public class MapSolver implements Updatable {
    private MapSolverState state;
    private Intersection[][] map;
    private ArrayList<WindDirections> directionsNESW;
    private ArrayList<Instructions> instructions;
    private int tentativeDistance;
    private int mapWidth;
    private int mapHeight;
    private boolean firstCall;

    private int startingX;
    private int startingY;
    private int currentX;
    private int currentY;
    private int endX;
    private int endY;

    /**
     * constructor that enables the map solver directly
     *
     * @param map       takes a new map of intersections
     * @param startingX starting x coordinate
     * @param startingY starting y coordinate
     * @param endX      ending x coordinate
     * @param endY      ending y coordinate
     */
    public MapSolver(Intersection[][] map, int startingX, int startingY, int endX, int endY) {
        setMap(map);
        enableMapSolve(startingX, startingY, endX, endY);
    }

    /**
     * constructor that sets the map already
     *
     * @param map takes in a new map of intersections
     */
    public MapSolver(Intersection[][] map) {
        setMap(map);
    }

    /**
     * simple constructor that just sets the state
     */
    public MapSolver() {
        this.state = MapSolverState.Nothing;
    }

    @java.lang.Override
    public void update() {
        switch (this.state) {
            case SolvingMap:
                solveStep();
                break;
            case CreatingPath:
                createPathStep();
                break;
            case Done:
                //we know the path can be driven
                break;
            case Nothing:
                //the state is nothing because the function solve map hasn't been called or the map cant be completed
                break;
        }
    }

    /**
     * this function solves the map if it is solvable
     * this is done using the TODO
     * we give every intersection an value how far away from the start it is
     * the state changes if the end conditions are met and these are when there is no valid path or the end has been reached
     */
    private void solveStep() {
        this.tentativeDistance = this.map[this.currentX][this.currentY].getTentativeDistance() + 1;

        //check for all the intersections around the current intersection
        {
            //check intersection to the north
            try {
                if (!map[currentX][currentY + 1].isVisited() && !map[currentX][currentY + 1].isBlocked()) {
                    //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                    if (tentativeDistance < map[currentX][currentY + 1].getTentativeDistance())
                        map[currentX][currentY + 1].setTentativeDistance(tentativeDistance);
                }
            } catch (Exception exception) {
                //this intersection doesnt exist on the map or is blocked
            }

            //check the intersection to the east
            try {
                if (!map[currentX + 1][currentY].isVisited() && !map[currentX + 1][currentY].isBlocked()) {
                    //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                    if (tentativeDistance < map[currentX + 1][currentY].getTentativeDistance())
                        map[currentX + 1][currentY].setTentativeDistance(tentativeDistance);
                }
            } catch (Exception exception) {
                //this intersection doesnt exist on the map or is blocked
            }

            //check intersection to the south
            try {
                if (!map[currentX][currentY - 1].isVisited() && !map[currentX][currentY - 1].isBlocked()) {
                    //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                    if (tentativeDistance < map[currentX][currentY - 1].getTentativeDistance())
                        map[currentX][currentY - 1].setTentativeDistance(tentativeDistance);
                }
            } catch (Exception exception) {
                //this intersection doesnt exist on the map or is blocked
            }

            //check intersection to the west
            try {
                if (!map[currentX - 1][currentY].isVisited() && !map[currentX - 1][currentY].isBlocked()) {
                    //if the tentative distance is smaller then intersection tentative distance set it to the current tentative distance
                    if (tentativeDistance < map[currentX - 1][currentY].getTentativeDistance())
                        map[currentX - 1][currentY].setTentativeDistance(tentativeDistance);
                }
            } catch (Exception exception) {
                //this intersection doesnt exist on the map or is blocked
            }
        }

        //set the current intersection as visited
        map[startingX][startingY].setVisited(true);

        //temperately variables
        int smallestTentativeDistance = Integer.MAX_VALUE;
        int nextX = currentX;
        int nextY = currentY;


        //go through all the intersections of the map and gets the smallest tentative distance from the not visited nodes
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (!map[x][y].isVisited()) {
                    if (map[x][y].getTentativeDistance() < smallestTentativeDistance) {
                        smallestTentativeDistance = map[x][y].getTentativeDistance();
                        nextX = x;
                        nextY = y;
                    }
                }
            }
        }

        //smallest tentative distance will be the next current intersection
        currentX = nextX;
        currentY = nextY;

        //the end condition is if the end point has been reached
        if (map[endX][endY].isVisited())
            this.state = MapSolverState.CreatingPath;
        //when there is no intersection left we can check we know there is no valid route to the exit
        if (smallestTentativeDistance == Integer.MAX_VALUE)
            this.state = MapSolverState.Nothing;
    }

    /**
     * this function creates the fastest path, we go from end to start
     * every iteration the right direction is added to the array list of directions
     * when done the state changes to done and the instruction array will be filled
     */
    private void createPathStep() {

        if (firstCall) {
            //set curent to the end point
            currentX = endX;
            currentY = endY;
            firstCall = false;
        }

        int smallestTentativeDistance = Integer.MAX_VALUE;
        //make sure the variable is not unsigned
        WindDirections gotoDirection = WindDirections.North;

        //check all the directions for the lowest tentative distance

        //check intersection to the north
        try {
            //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
            if (smallestTentativeDistance > this.map[currentX][currentY - 1].getTentativeDistance()) {
                smallestTentativeDistance = this.map[currentX][currentY - 1].getTentativeDistance();
                gotoDirection = WindDirections.North;
            }
        } catch (Exception exception) {
            //this intersection doesnt exist on the map
        }

        //check the intersection to the east
        try {
            //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
            if (smallestTentativeDistance > this.map[currentX + 1][currentY].getTentativeDistance()) {
                smallestTentativeDistance = this.map[currentX + 1][currentY].getTentativeDistance();
                gotoDirection = WindDirections.East;
            }
        } catch (Exception exception) {
            //this intersection doesnt exist on the map
        }

        //check intersection to the south
        try {
            //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
            if (smallestTentativeDistance > this.map[currentX][currentY - 1].getTentativeDistance()) {
                smallestTentativeDistance = this.map[currentX][currentY - 1].getTentativeDistance();
                gotoDirection = WindDirections.South;
            }
        } catch (Exception exception) {
            //this intersection doesnt exist on the map
        }

        //check intersection to the west
        try {
            //if the smallest tentative distance is bigger then intersection tentative distance that is the new smallest distance
            if (smallestTentativeDistance > this.map[currentX - 1][currentY].getTentativeDistance()) {
                smallestTentativeDistance = this.map[currentX - 1][currentY].getTentativeDistance();
                gotoDirection = WindDirections.West;
            }
        } catch (Exception exception) {
            //this intersection doesnt exist on the map
        }

        this.directionsNESW.add(gotoDirection);
        //end condition is when the starting point has been reached
        if (currentX == startingX && currentY == startingY) {
            this.instructions = converterFromWindDirectionsToInstructions(this.directionsNESW);
            this.firstCall = true;
            this.state = MapSolverState.Done;
        }
    }


    /**
     * this method gets the shortest path to the exit when given a map of intersections using the Dijkstra algorithm
     *
     * @param startingX is the starting coordinate x
     * @param startingY is the starting coordinate y
     * @param endX      is the end coordinate x
     * @param endY      is the end coordinate y
     */
    public void enableMapSolve(int startingX, int startingY, int endX, int endY) {
        //let the system know it's trying to solve the map
        this.state = MapSolverState.SolvingMap;

        //reset the variable and clear the array list
        this.tentativeDistance = 0;
        this.directionsNESW.clear();
        this.startingX = startingX;
        this.startingY = startingY;
        this.endX = endX;
        this.endY = endY;


        //set the starting point tentative distance and the end point
        map[startingX][startingY].setTentativeDistance(tentativeDistance);
        map[endX][endY].setTentativeDistance(tentativeDistance);

        //this is where we keep the currentCoordinates coordinates
        this.currentX = this.startingX;
        this.currentY = this.startingY;
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

    public ArrayList<WindDirections> getDirectionsNESW() {
        return directionsNESW;
    }

    public Intersection[][] getMap() {
        return map;
    }

    public MapSolverState getState() {
        return state;
    }

    /**
     * this function resets the mapsolver and makes the parameter the new map
     *
     * @param map new map that will be used to make a route
     */
    public void setMap(Intersection[][] map) {
        //determines the map width and height the try and catch is to make sure there isn't a exception thrown
        try {
            while (map[mapWidth][0] != null) {
                mapWidth++;
            }
            while (map[0][mapHeight] != null) {
                mapHeight++;
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
        this.state = MapSolverState.Nothing;
        this.directionsNESW = new ArrayList<>();
        this.map = map;
        this.firstCall = true;
    }

}
