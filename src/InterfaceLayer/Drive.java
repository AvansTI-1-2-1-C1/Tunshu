package InterfaceLayer;

import HardwareLayer.Motor;
import TI.BoeBot;
import TI.Timer;

public class Drive {

    private Motor left;
    private Motor right;
    private int speed;
    private boolean forwards;
    private boolean backwards;
    private int oldSpeed;

    public Drive(int servoLeft, int servoRight) {
        this.left = new Motor(servoLeft);
        this.right = new Motor(servoRight);
        this.speed = 0;
        this.oldSpeed = 0;
        this.forwards = true;
        this.backwards = true;
    }

    public void followLine(){
        Timer t4 =  new Timer(50);
        if(t4.timeout()) {
            while (BoeBot.analogRead(1) < 1500) {
                Timer t3 = new Timer(30);
                if (t3.timeout()) {
                    int counter4 = 5;
                    int counter2 = 20;
                    while (BoeBot.analogRead(0) > 1500) {
                        Timer t1 = new Timer(20);
                        if (t1.timeout()) {
                            this.left.setSpeed(1550 + counter2);
                            this.right.setSpeed(1460 + counter4);
                            counter2 = counter2 + 10;
                            counter4++;
                            t1.mark();
                        }
                    }
                    int counter1 = 20;
                    int counter3 = 5;
                    while (BoeBot.analogRead(2) > 1500) {
                        Timer t2 = new Timer(20);
                        if (t2.timeout()) {
                            this.left.setSpeed(1540 - counter3);
                            this.right.setSpeed(1450 - counter1);
                            counter1 = counter1 + 10;
                            counter3++;
                            t2.mark();
                        }
                    }
                    t3.mark();
                }
            }
            this.oldSpeed = this.speed;
            this.speed = 100;
            this.accelerate();
            t4.mark();
        }
    }

    public void accelerate() {
        if (this.forwards) {
            while (this.oldSpeed < this.speed) {
                this.oldSpeed++;
                this.left.setSpeed(1500 + this.oldSpeed);
                this.right.setSpeed(1500 - this.oldSpeed);
                BoeBot.wait(10);
            }
        } else if (this.backwards) {
            while (this.oldSpeed < this.speed) {
                this.oldSpeed++;
                this.left.setSpeed(1500 - this.oldSpeed);
                this.right.setSpeed(1500 + this.oldSpeed);
                BoeBot.wait(10);
            }
        }
    }

    public void decelerate() {
        if (this.backwards) {
            while (this.oldSpeed > this.speed) {
                this.oldSpeed--;
                this.left.setSpeed(1500 - this.oldSpeed);
                this.right.setSpeed(1500 + this.oldSpeed);
                BoeBot.wait(10);
            }
        } else if (this.forwards) {
            while (this.oldSpeed > this.speed) {
                this.oldSpeed--;
                this.left.setSpeed(1500 + this.oldSpeed);
                this.right.setSpeed(1500 - this.oldSpeed);
                BoeBot.wait(10);
            }
        }
    }


    public void turnLeft() {
        if (this.speed > 0) {
            if (this.forwards) {
                this.right.setSpeed(1450 - this.speed);
                BoeBot.wait(1700);
                this.right.setSpeed(1500 - this.speed);
            } else if (this.backwards) {
                this.right.setSpeed(1550 + this.speed);
                BoeBot.wait(1700);
                this.right.setSpeed(1500 + this.speed);
            }
        } else {
            this.left.setSpeed(1450);
            this.right.setSpeed(1450);
            BoeBot.wait(775);
            this.left.setSpeed(1500);
            this.right.setSpeed(1500);
        }
    }

    public void turnRight() {

        if (this.speed > 0) {
            if (this.forwards) {
                this.left.setSpeed(1550 + this.speed);
                BoeBot.wait(850);
                this.left.setSpeed(1500 + this.speed);
            } else if (this.backwards) {
                this.left.setSpeed(1450 - this.speed);
                BoeBot.wait(850);
                this.left.setSpeed(1500 - this.speed);
            }
        } else {
            this.left.setSpeed(1550);
            this.right.setSpeed(1550);
            BoeBot.wait(775);
            this.left.setSpeed(1500);
            this.right.setSpeed(1500);
        }
    }

    public void handBreak() {
        this.right.setSpeed(1500);
        this.left.setSpeed(1500);
    }

    public void increaseSpeed() {
        if (this.speed < 200) {
            this.oldSpeed = this.speed;
            this.speed += 50;
            accelerate();
        }
    }

    public void decreaseSpeed() {
        if (this.speed > 0) {
            this.oldSpeed = this.speed;
            this.speed -= 50;
            decelerate();
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isForwards() {
        return forwards;
    }

    public void setForwards(boolean forwards) {
        this.forwards = forwards;
    }

    public boolean isBackwards() {
        return backwards;
    }

    public void setBackwards(boolean backwards) {
        this.backwards = backwards;
    }

    public void setOldSpeed(int oldSpeed) {
        this.oldSpeed = oldSpeed;
    }
}
