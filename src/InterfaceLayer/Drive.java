import TI.BoeBot;
import TI.Servo;


public class Drive {

    private Servo left;
    private Servo right;
    private int speed;
    private boolean forwards;
    private boolean backwards;


    public Drive(int servoLeft, int servoRight) {
        this.left = new Servo(servoLeft);
        this.right = new Servo(servoRight);
        this.left.start();
        this.right.start();
        this.speed = 0;
        this.forwards = true;
        this.backwards = true;
    }

    public void accelerate(int oldSpeed) {
        if(this.forwards) {
            while (oldSpeed < this.speed) {
                oldSpeed++;
                this.left.update(1500 + oldSpeed);
                this.right.update(1500 - oldSpeed);
                BoeBot.wait(10);
            }
        }else if(this.backwards){
            while (oldSpeed < this.speed) {
                oldSpeed++;
                this.left.update(1500 - oldSpeed);
                this.right.update(1500 + oldSpeed);
                BoeBot.wait(10);
            }
        }
    }

    public void decelerate(int oldSpeed) {
        if(this.backwards) {
            while (oldSpeed > this.speed) {
                oldSpeed--;
                this.left.update(1500 - oldSpeed);
                this.right.update(1500 + oldSpeed);
                BoeBot.wait(10);
            }
        }else if(this.forwards){
            while (oldSpeed > this.speed) {
                oldSpeed--;
                this.left.update(1500 + oldSpeed);
                this.right.update(1500 - oldSpeed);
                BoeBot.wait(10);
            }
        }
    }


    public void turnLeft() {
        if(this.speed > 0) {
            this.right.update(1450 - this.speed);
            BoeBot.wait(850);
            this.right.update(1500 - this.speed);
        }else {
            this.left.update(1450);
            this.right.update(1450);
            BoeBot.wait(775);
            this.left.update(1500);
            this.right.update(1500);
        }
    }

    public void turnRight() {

        if(this.speed > 0) {
            this.left.update(1550 + this.speed);
            BoeBot.wait(850);
            this.left.update(1500 + this.speed);
        }else {
            this.left.update(1550);
            this.right.update(1550);
            BoeBot.wait(775);
            this.left.update(1500);
            this.right.update(1500);
        }
    }

    public void handBreak() {
        this.right.update(1500);
        this.left.update(1500);
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
