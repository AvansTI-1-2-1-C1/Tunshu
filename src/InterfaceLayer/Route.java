package InterfaceLayer;

import java.util.ArrayList;

public class Route {

    private ArrayList<String> directions;
    private int instructionCounter;

    public Route() {
        this.instructionCounter = 0;
        this.directions = new ArrayList<>();
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions.addAll(directions);
        System.out.println(this.directions);

    }

    public String getDirection(){
        String direction = "";
        System.out.println(directions);//TODO
        if (this.directions.isEmpty()){
            direction = "none";
        }else {
            direction = this.directions.get(0);
            directions.remove(0);
        }

        this.instructionCounter = this.instructionCounter+1;
        return direction;
    }

}
