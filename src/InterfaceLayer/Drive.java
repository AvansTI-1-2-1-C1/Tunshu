package InterfaceLayer;

import HardwareLayer.Motor;
import HeadInterfaces.Updatable;
import TI.BoeBot;
import TI.Timer;

public class Drive implements Updatable {

    protected Motor left;
    protected Motor right;
    private int speed;
    private boolean forwards;
    private boolean backwards;
    private int oldSpeed;
    private Timer circelTimer;

    public Drive() {
        this.left = new Motor(12);
        this.right = new Motor(13);
        this.speed = 0;
        this.oldSpeed = 0;
        this.forwards = true;
        this.backwards = true;
    }

    public void followLine(){

    }

    public void accelerate(int speed) {
        this.oldSpeed = this.speed;
        this.speed = speed;
        //Accelerates the Boebot slowly forward
        if (this.forwards) {
            while (this.oldSpeed < this.speed) {
                this.oldSpeed++;
                this.left.setSpeed(1500 + this.oldSpeed);
                this.right.setSpeed(1500 - this.oldSpeed);
                BoeBot.wait(10);
            }
        }
        //Accelerates the Boebot slowly backwards
        else if (this.backwards) {
            while (this.oldSpeed < this.speed) {
                this.oldSpeed++;
                this.left.setSpeed(1500 - this.oldSpeed);
                this.right.setSpeed(1500 + this.oldSpeed);
                BoeBot.wait(10);
            }
        }
    }

    public void decelerate(int speed) {
        this.oldSpeed = this.speed;
        this.speed = speed;
        //Decelerates the Boebot slowly
        if (this.forwards) {
            while (this.oldSpeed > this.speed) {
                this.oldSpeed--;
                this.left.setSpeed(1500 + this.oldSpeed);
                this.right.setSpeed(1500 - this.oldSpeed);
                BoeBot.wait(10);
            }
        } else if (this.backwards) {
            while (this.oldSpeed > this.speed) {
                this.oldSpeed--;
                this.left.setSpeed(1500 - this.oldSpeed);
                this.right.setSpeed(1500 + this.oldSpeed);
                BoeBot.wait(10);
            }
        }
    }


    public void turnLeft() {
        //Turns the Boebot to the left while driving
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
        }
        //Turns the Boebot left on its axis
        else {
            this.left.setSpeed(1450);
            this.right.setSpeed(1450);
            BoeBot.wait(775);
            this.left.setSpeed(1500);
            this.right.setSpeed(1500);
        }
    }

    public void turnRight() {
        //Turns the Boebot to the right while driving
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
        }
        //Turns the Boebot to the right on its axis
        else {
            this.left.setSpeed(1550);
            this.right.setSpeed(1550);
            BoeBot.wait(775);
            this.left.setSpeed(1500);
            this.right.setSpeed(1500);
        }
    }

    public void handBreak() {
        //Stops the Boebot instantly
        this.right.setSpeed(1500);
        this.left.setSpeed(1500);
    }

    public void increaseSpeed() {
        int speed = this.speed;
        //Increases the speed of the Boebot by 50
        if (this.speed < 200) {
            speed += 50;
            accelerate(speed);
        }
        this.speed = speed;
    }

    public void decreaseSpeed() {
        int speed = this.speed;
        //Decreases the speed of the Boebot by 50
        if (this.speed > 0) {
            speed -= 50;
            decelerate(speed);
        }
        this.speed = speed;
    }

    public void cirkel(){
        if(!circelTimer.timeout()) {
            circelTimer.setInterval(800);
            this.left.setSpeed(1500 + speed);
            this.right.setSpeed(1450 - speed);
        }else {
            this.accelerate(this.speed);
            circelTimer.mark();
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

    @java.lang.Override
    public void update() {

    }
}
