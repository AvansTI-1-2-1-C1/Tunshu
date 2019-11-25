package InterfaceLayer;

import HardwareLayer.Motor;
import TI.BoeBot;

public class Drive {

    private Motor left;
    private Motor right;
    private int speed;
    private boolean forwards;
    private boolean backwards;

    public Drive(int servoLeft, int servoRight) {
        this.left = new Motor(servoLeft);
        this.right = new Motor(servoRight);
        this.speed = 0;
        this.forwards = true;
        this.backwards = true;
    }

    public void accelerate(int oldSpeed) {
        if (this.forwards) {
            while (oldSpeed < this.speed) {
                oldSpeed++;
                this.left.setSpeed(1500 + oldSpeed);
                this.right.setSpeed(1500 - oldSpeed);
                BoeBot.wait(10);
            }
        } else if (this.backwards) {
            while (oldSpeed < this.speed) {
                oldSpeed++;
                this.left.setSpeed(1500 - oldSpeed);
                this.right.setSpeed(1500 + oldSpeed);
                BoeBot.wait(10);
            }
        }
    }

    public void decelerate(int oldSpeed) {
        if (this.backwards) {
            while (oldSpeed > this.speed) {
                oldSpeed--;
                this.left.setSpeed(1500 - oldSpeed);
                this.right.setSpeed(1500 + oldSpeed);
                BoeBot.wait(10);
            }
        } else if (this.forwards) {
            while (oldSpeed > this.speed) {
                oldSpeed--;
                this.left.setSpeed(1500 + oldSpeed);
                this.right.setSpeed(1500 - oldSpeed);
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
            int oldSpeed = this.speed;
            this.speed += 50;
            accelerate(oldSpeed);
        }
    }

    public void decreaseSpeed() {
        if (this.speed > 0) {
            int oldSpeed = this.speed;
            this.speed -= 50;
            decelerate(oldSpeed);
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
}
