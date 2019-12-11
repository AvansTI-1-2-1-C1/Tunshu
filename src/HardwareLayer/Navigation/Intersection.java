package HardwareLayer.Navigation;

public class Intersection {
    private boolean north;
    private boolean east;
    private boolean south;
    private boolean west;
    private int counter;


    public Intersection(boolean north, boolean east,boolean south, boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.counter = 1;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    public void count(){
        this.counter++;
    }

    @Override
    public String toString() {
        return ""+counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
