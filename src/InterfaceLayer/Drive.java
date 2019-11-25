package InterfaceLayer;

import HardwareLayer.Motor;
import TI.BoeBot;

public class Drive {

    private Motor left;
    private Motor right;
    private int speed;
    private boolean forwards;
    private boolean backwards;
    private boolean speaker;

    public Drive(int servoLeft, int servoRight) {
        this.left = new Motor(servoLeft);
        this.right = new Motor(servoRight);
        this.speed = 0;
        this.forwards = true;
        this.backwards = true;
        this.speaker = false;
    }

    public void control(int button) {
        switch (button) {
            case 0:
                break;

            case 1:
                System.out.println("Stop");
                this.handBreak();
                this.speed = 0;
                this.decelerate(this.speed);
                this.handBreak();
                break;

            case 144:
                System.out.println("Vooruit");
                if (this.backwards) {
                    System.out.println("Eerst stop");
                    int oldSpeed = this.speed;
                    this.speed = 0;
                    this.decelerate(oldSpeed);
                    this.backwards = false;
                }
                if (this.forwards) {
                    this.speed = 50;
                    this.accelerate(0);
                }
                this.forwards = true;
                break;

            // Boebot gaat vooruit
            case 2192:
                System.out.println("Achteruit");
                if (this.forwards) {
                    System.out.println("Eerst stop");
                    int oldSpeed = this.speed;
                    this.speed = 0;
                    this.decelerate(oldSpeed);
                    this.forwards = false;
                }
                if (this.backwards) {
                    this.speed = 50;
                    this.accelerate(0);
                }
                this.backwards = true;
                break;

            //Boebot gaat achteruit
            case 3216:
                System.out.println("Links");
                this.turnLeft();
                break;

            //Boebot gaat naar links
            case 1168:
                System.out.println("Rechts");
                this.turnRight();
                break;

            //Boebot gaat naar rechts
            case 2704:
                System.out.println("Fullstop");
                this.forwards = false;
                this.backwards = false;
                this.handBreak();
                this.speed = 0;
                break;

            case 1936:
                System.out.println("Sneller");
                this.increaseSpeed();
                break;

            case 3984:
                System.out.println("Langzamer");
                this.decreaseSpeed();
                break;
            case 656:
                if (!this.speaker){

                    this.speaker = true;
                }else {
                    this.speaker = false;
                }

        }
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
}
