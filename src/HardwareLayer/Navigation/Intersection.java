package HardwareLayer.Navigation;


public class Intersection {
    private int tentativeDistance;
    private boolean isVisited;

    /**
     * constructor sets the intersection to has not been visited and sets the tentative distance to infinite
     */
    public Intersection() {
        this.tentativeDistance = Integer.MAX_VALUE;
        this.isVisited = false;
    }

    @Override
    public String toString() {
        return ""+ tentativeDistance;
    }

    public int getTentativeDistance() {
        return tentativeDistance;
    }

    public void setTentativeDistance(int tentativeDistance) {
        this.tentativeDistance = tentativeDistance;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isVisited() {
        return isVisited;
    }
}
