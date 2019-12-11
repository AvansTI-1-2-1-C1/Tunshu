package InterfaceLayer;

import HeadInterfaces.Updatable;

import java.util.ArrayList;

public class Route implements Updatable {

    private ArrayList<String> directions;
    private int instructionCounter;

    public Route() {
        this.instructionCounter = 0;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public String getDirection(){
        String direction = directions.get(instructionCounter);
        this.instructionCounter = this.instructionCounter+1;
        return direction;
    }

    @java.lang.Override
    public void update() {

    }
}
