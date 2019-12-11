package InterfaceLayer;

import HardwareLayer.Navigation.Intersection;


public class MakeMap {
    private Intersection[][] intersections;
    private int x = 20;
    private int y = 20;
    private char facingDirection;
    private RouteFollower routeFollower;

    public MakeMap(RouteFollower routeFollower) {
        intersections = new Intersection[40][40];
        facingDirection = 'N';
        this.routeFollower = routeFollower;
    }

//    /**
//     * every time this function gets called the method wil save the intersection and then proceeds to the next logical intersection
//     *
//     * @param front these are boolean you have to set with if there are walls
//     * @param right
//     * @param left
//     */
//    public void addIntersection(boolean front, boolean right, boolean left) {
//
//        if (intersections[x][y] != null) {
//            intersections[x][y].count();
//        }
//
//        switch (facingDirection) {
//            case 'N':
//                if (intersections == null){
//                    intersections[x][y] = new Intersection(front, right, true, left);
//                }
//                if (right) {
//                    facingDirection = 'E';
//                    routeFollower.off();
//                    //turn right
//                    routeFollower.turnRight();
//                    //add x because going right once
//                    routeFollower.on();
//                    x++;
//                } else if (front) {
//                    //go foward
//                    routeFollower.off();
//                    routeFollower.goForward();
//                    //y changes
//                    routeFollower.on();
//
//                    y++;
//                } else if (left) {
//                    facingDirection = 'W';
//                    //turn left
//                    routeFollower.off();
//                    routeFollower.turnLeft();
//                    //x changes going left once
//                    routeFollower.on();
//
//                    x--;
//                } else {
//                    facingDirection = 'S';
//                    //turn 180 degrees
//                    routeFollower.off();
//                    routeFollower.turnBack();
//                    //goes back once
//                    routeFollower.on();
//
//                    y--;
//                }
//                break;
//            case 'E':
//                if (intersections[x][y] == null){
//                    intersections[x][y] = new Intersection(front, right, true, left);
//                }
//                if (right) {
//                    facingDirection = 'S';
//                    //turn right
//                    routeFollower.off();
//                    routeFollower.turnRight();
//                    routeFollower.on();
//
//                    //add x because going right once
//                    y--;
//                } else if (front) {
//                    //go foward
//                    routeFollower.off();
//                    routeFollower.goForward();
//                    routeFollower.on();
//
//
//                    //y changes
//                    x++;
//                } else if (left) {
//                    facingDirection = 'N';
//                    //turn left
//                    routeFollower.off();
//                    routeFollower.turnLeft();
//                    routeFollower.on();
//
//                    //x changes going left once
//                    y++;
//                } else {
//                    facingDirection = 'W';
//                    //turn 180 degrees
//                    routeFollower.off();
//                    routeFollower.turnBack();
//
//                    routeFollower.on();
//
//                    //goes back once
//                    x--;
//                }
//                break;
//            case 'S':
//                if (intersections[x][y] == null) {
//                    intersections[x][y] = new Intersection(true, left, front, right);
//                }
//                if (right) {
//                    facingDirection = 'W';
//                    //turn right
//                    routeFollower.off();
//                    routeFollower.turnRight();
//                    routeFollower.on();
//
//                    //add x because going right once
//                    x--;
//                } else if (front) {
//                    //go foward
//                    routeFollower.off();
//                    routeFollower.goForward();
//                    routeFollower.on();
//
//                    //y changes
//                    y--;
//                } else if (left) {
//                    facingDirection = 'E';
//                    //turn left
//                    routeFollower.off();
//                    routeFollower.turnLeft();
//                    routeFollower.on();
//
//                    //x changes going left once
//                    x++;
//                } else {
//                    facingDirection = 'N';
//                    //turn 180 degrees
//                    routeFollower.off();
//                    routeFollower.turnBack();
//
//                    routeFollower.on();
//
//                    //goes back once
//                    y++;
//                }
//                break;
//            case 'W':
//                if (intersections[x][y] == null) {
//                    intersections[x][y] = new Intersection(right, true, left, front);
//                }
//                if (right) {
//                    facingDirection = 'N';
//                    //turn right
//                    routeFollower.off();
//                    routeFollower.turnRight();
//                    routeFollower.on();
//
//                    y++;
//                } else if (front) {
//                    //go foward
//                    routeFollower.off();
//                    routeFollower.goForward();
//                    routeFollower.on();
//
//
//                    x--;
//                } else if (left) {
//                    facingDirection = 'S';
//                    //turn left
//                    routeFollower.off();
//                    routeFollower.turnLeft();
//                    routeFollower.on();
//
//                    y--;
//                } else {
//                    facingDirection = 'E';
//                    //turn 180 degrees
//                    routeFollower.off();
//                    routeFollower.turnBack();
//                    routeFollower.on();
//
//                    x++;
//                }
//                break;
//        }
//    }

    public void printMap() {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (intersections[i][j] == null) {
                    System.out.print("0");
                } else {
                    System.out.print(intersections[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }


    

}
