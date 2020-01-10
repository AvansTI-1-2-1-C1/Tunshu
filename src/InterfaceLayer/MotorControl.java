package InterfaceLayer;

import HardwareLayer.Motor;
import InterfaceLayer.HeadInterfaces.Updatable;
import TI.Timer;
import Utils.Enums.Instructions;

public class MotorControl implements Updatable {

    private Motor motorLeft;
    private Motor motorRight;

    private final float speedStep; // the amount that is added to the speed when speeding up
    private final float turnRateStep; // the amount that is added each step to the turn
    private Timer timer = new Timer(50); // the time until the next change in milli seconds

    private float currentSpeed;
    private float targetSpeed;
    private float currentTurnRate;
    private float targetTurnRate;

    //makes sure the vehicle doesn't drive if true
    private boolean isLocked;
    private boolean isDrivingBackward;
    private boolean slowAccelerate;

    private boolean isTurning;

    public MotorControl() {
        this.motorLeft = new Motor(12);
        this.motorRight = new Motor(13);
        speedStep = 0.025F;
        turnRateStep = 0.1F;
        isLocked = false;
        isDrivingBackward = false;
        slowAccelerate = true;
        isTurning = false;
    }

    /**
     * Set the target speed and target turn rate
     *
     * @param speed    how fast we want the bot to drive
     *                 this is done with a float where 1 is full power forward and -1 is backward and 0 is standing still
     * @param turnRate how fast we want the bot to turn
     *                 this is done with a float where 1 is full counter clockwise turning, -1 is clockwise turning
     *                 and 0 is standing still
     */
    public void setMotorsTarget(float speed, float turnRate) {
        targetSpeed = speed;
        targetTurnRate = turnRate;
    }

    /**
     * this is the method that need to be called every update loop so the motors get adjusted properly
     */
    public void update() {
        if (isTurning)
            return;


        if (!isLocked) {

            if (slowAccelerate) {
                //timer so the bot accelerates slowly
                if (timer.timeout()) {

                    //set the current speed speed step closer to target speed
                    currentSpeed = currentToTargeted(currentSpeed, targetSpeed, speedStep);

                    //set the current turn rate closer to targeted turn rate with turn rate amount
                    currentTurnRate = currentToTargeted(currentTurnRate, targetTurnRate, turnRateStep);

                    //set the motors to the recently calculated amounts
                    setBotSpeed(currentSpeed, currentTurnRate);

                    //reset the timer
                    timer.mark();

                    //to make sure the notification system knows it is driving backwards
                    isDrivingBackward = currentSpeed < 0;
                }
            } else {
                setBotSpeed(targetSpeed, targetTurnRate);
            }
        } else {
            setBotSpeed(0, 0);
        }
    }


    /**
     * method to set the current closer to the target with step amount
     *
     * @param current    is an float what it currently is
     * @param target     is an float what we want current to be
     * @param stepAmount is the amount that is added or subtracted to current to get closer to target
     * @return altered current
     */
    private float currentToTargeted(float current, float target, float stepAmount) {
        // if current is smaller then the target add the step amount
        if (current < target) {
            current += stepAmount;
            // if then the current is bigger we know we over shot it so we set it to the target
            if (current > target) {
                current = target;
            }
            // if current is bigger then the target add the step amount
        } else if (current > target) {
            current -= stepAmount;
            // if then the current is smaller we know we over shot it so we set it to the target
            if (current < target) {
                current = target;
            }
        }
        return current;
    }

    /**
     * Calculates the speed of both motors
     * we make one motor go slower then the other to make a turn instead of trying to speed one motor up
     * this way we don't have a problem turning while at max speed
     * this is done by multiplying the speed with the turn rate reversed percentile
     *
     * @param speed    this is done with a float where 1 is full power forward and -1 is backward and 0 is standing still
     * @param turnRate this is done with a float where 1 is full counter clockwise turning, -1 is clockwise turning
     *                 and 0 is standing still
     */
    private void setBotSpeed(float speed, float turnRate) {
        float motorLeftSpeed;
        float motorRightSpeed;
        if (turnRate == 0) {
            //drive straight
            motorLeftSpeed = speed;
            motorRightSpeed = speed;
        } else if (turnRate > 0f) {
            // turn right
            motorLeftSpeed = speed;
            motorRightSpeed = (speed * (1 - turnRate));
        } else {
            // turn left
            motorLeftSpeed = (speed * (1 + turnRate));
            motorRightSpeed = speed;
        }

        motorLeft.update(motorLeftSpeed, false);
        motorRight.update(motorRightSpeed, true);
    }

    public void rotate(Instructions direction) {
        this.isTurning = true;
        switch (direction) {
            case Right:
                //turn 90 degrees right
                motorLeft.update(.3f, false);
                motorRight.update(.3f, false);

                break;
            case Left:
                //turn 90 degrees left
                motorLeft.update(.3f, true);
                motorRight.update(.3f, true);

                break;
            case Forward:
                // drive straight
                motorLeft.update(0.3f, false);
                motorRight.update(0.3f, true);
                break;
            case None:
                motorLeft.update(0, false);
                motorRight.update(0, false);
                break;
            default:
                System.out.println(direction + " is unknown");
                break;
        }
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public float getCurrentTurnRate() {
        return currentTurnRate;
    }

    public void setLocked(boolean isLocked) {
        motorLeft.update(0, false);
        motorRight.update(0, false);
        setMotorsTarget(0,0);
        this.isLocked = isLocked;
    }

    public void setTurning(boolean turning) {
        isTurning = turning;
    }

    public boolean isDrivingBackward() {
        return isDrivingBackward;
    }

    public void setSlowAccelerate(boolean slowAccelerate) {
        this.slowAccelerate = slowAccelerate;
    }

    public boolean isLocked() {
        return isLocked;
    }
}

