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
    private int servoMotorMiddle;

    public Drive() {
        this.left = new Motor(12);
        this.right = new Motor(13);
        this.speed = 0;
        this.oldSpeed = 0;
        this.forwards = true;
        this.backwards = false;
        this.circelTimer = new Timer(1500);
        this.servoMotorMiddle = 1500;
    }

    public void followLine(){

    }

    public void accelerate(int speed) {
        this.oldSpeed = this.speed;
        this.speed = speed;
        //Accelerates the Boebot slowly forward
        if (this.forwards) {
            //@todo we dont want to use a while loop and a wait here because while we accelerate we cant detect anything
            while (this.oldSpeed < this.speed) {
                this.oldSpeed++;
                this.left.setSpeed(1500 + this.oldSpeed);
                this.right.setSpeed(1500 - this.oldSpeed);
                BoeBot.wait(10);
            }
        }
        //Accelerates the Boebot slowly backwards
        else if (this.backwards) {
            //@todo we dont want to use a while loop and a wait here because while we accelerate we cant detect anything
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
            //@todo we dont want to use a while loop and a wait here because while we accelerate we cant detect anything
            while (this.oldSpeed > this.speed) {
                this.oldSpeed--;
                this.left.setSpeed(1500 + this.oldSpeed);
                this.right.setSpeed(1500 - this.oldSpeed);
                BoeBot.wait(10);
            }
        } else if (this.backwards) {
            //@todo we dont want to use a while loop and a wait here because while we accelerate we cant detect anything
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
                //@todo
                BoeBot.wait(850);
                this.right.setSpeed(1500 - this.speed);
            } else if (this.backwards) {
                this.right.setSpeed(1550 + this.speed);
                //@todo
                BoeBot.wait(850);
                this.right.setSpeed(1500 + this.speed);
            }
        }
        //Turns the Boebot left on its axis
        else {
            this.left.setSpeed(1450);
            this.right.setSpeed(1450);
            //@todo
            BoeBot.wait(775);
            this.left.setSpeed(1500);
            this.right.setSpeed(1500);
        }
    }

    /**
     * turning the BoeBot during time milliseconds and with the speed of turnSpeed.
     *
     * @param time in milliseconds
     * @param turnSpeed how fast the other servo motor is going, given as a positive integer turns right else turns left
     */
    public void turn(int time, int turnSpeed) {
        boolean turning = true;
        Timer turnLength = new Timer(time);
        turnLength.mark();
        //check if the BoeBot is already moving
        if (this.speed > 0) {
            if (this.forwards) {
                while (turning) {
                    //updates the hit detection so we stay safe
                    //@todo add hitDetection.update() or something

                    //set the speed of the motor to 50 more then the speed so it turns
                    this.left.setSpeed(servoMotorMiddle + speed + turnSpeed);
                    if (turnLength.timeout()) {
                        //set the motor to the speed it was
                        this.left.setSpeed(servoMotorMiddle + speed);
                        turning = false;
                    }
                }
            } else {
                while (turning) {
                    //updates the hit detection so we stay safe
                    //@todo add hitDetection.update() or something

                    //set the speed of the motor to 50 more then the speed so it turns
                    this.left.setSpeed(servoMotorMiddle - speed + turnSpeed);
                    if (turnLength.timeout()) {
                        //set the motor to the speed it was
                        this.left.setSpeed(servoMotorMiddle - speed);
                        turning = false;
                    }
                }
            }
        }
        //the BoeBot is not moving
        else {
            while (turning) {
                //updates the hit detection so we stay safe
                //@todo add hitDetection.update() or something

                //set the speed of the motors to the turnSpeed, one clockwise and one counter clockwise
                this.left.setSpeed(servoMotorMiddle + turnSpeed);
                this.right.setSpeed(servoMotorMiddle + turnSpeed);
                if (turnLength.timeout()) {
                    //set the motors still at the end of the time
                    this.left.setSpeed(servoMotorMiddle);
                    this.right.setSpeed(servoMotorMiddle);
                    turning = false;
                }
            }
        }
    }


    public void turnRight() {
        //Turns the Boebot to the right while driving
        if (this.speed > 0) {
            if (this.forwards) {
                this.left.setSpeed(1550 + this.speed);
                //@todo
                BoeBot.wait(850);
                this.left.setSpeed(1500 + this.speed);
            } else if (this.backwards) {
                this.left.setSpeed(1450 - this.speed);
                //@todo
                BoeBot.wait(850);
                this.left.setSpeed(1500 - this.speed);
            }
        }
        //Turns the Boebot to the right on its axis
        else {
            this.left.setSpeed(1550);
            this.right.setSpeed(1550);
            //@todo
            BoeBot.wait(775);
            this.left.setSpeed(1500);
            this.right.setSpeed(1500);
        }
    }

    public void handbrake() {
        //Stops the Boebot instantly
        this.right.setSpeed(1500);
        this.left.setSpeed(1500);
    }

    public void increaseSpeed() {
        int speed = this.speed;
        //Increases the speed of the Boebot by 50
        if (this.speed < 200 && this.speed < 200) {
            speed += 50;
            accelerate(speed);
        }
        this.speed = speed;
    }

    public void decreaseSpeed() {
        int speed = this.speed;
        //Decreases the speed of the Boebot by 50
        if (this.speed > 0 && this.speed < 200) {
            speed -= 50;
            decelerate(speed);
        }
        this.speed = speed;
    }

    public void cirkel() {
        if (!circelTimer.timeout()) {
            this.left.setSpeed(1550 + speed);
            this.right.setSpeed(1350 - speed);
        } else {
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
