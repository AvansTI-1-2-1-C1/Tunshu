package InterfaceLayer;

import java.util.ArrayList;

public class Route {

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
        String direction = "";
        try {
            direction = directions.get(instructionCounter);
        }catch (Exception e){
            System.out.println(e);
            direction = "none";
        }
        this.instructionCounter = this.instructionCounter+1;
        return direction;
    }

}
