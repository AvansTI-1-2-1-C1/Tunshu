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

    public Character getDirection(){
        char direction = directions.get(instructionCounter);
        this.instructionCounter = this.instructionCounter+1;
        return direction;
    }

    @java.lang.Override
    public void update() {

    }
}
