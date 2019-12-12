package InterfaceLayer;

import Utils.Enums.Directions;

import java.util.ArrayList;

public class Route {

    private ArrayList<Directions> directions;
    private int instructionCounter;

    public Route() {
        this.instructionCounter = 0;
        this.directions = new ArrayList<>();
    }

    public ArrayList<Directions> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<Directions> directions) {
        this.directions.addAll(directions);
        System.out.println(this.directions);//TODO

    }

    public Directions getDirection(){
        Directions direction;
        System.out.println(directions);//TODO
        if (this.directions.isEmpty()){
            direction = Directions.None;
        }else {
            direction = this.directions.get(0);
            directions.remove(0);
        }

        this.instructionCounter = this.instructionCounter+1;
        return direction;
    }

}
