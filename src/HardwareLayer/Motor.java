package HardwareLayer;

import HeadInterfaces.Updatable;

public class Motor {

    private int pin;
    private TI.Servo servo;

    private final int BASEPULSEWIDTH = 1500; // pulse length when motor is standing still
    private final int MAXPULSEWIDTH = 200; // the difference in pulse length to max speed and standing still

    /**
     * constructor to set the pin number and initiate the servo
     * @param pin   int that determines where to send the data to
     */
    public Motor(int pin) {
        this.pin = pin;
        this.servo = new TI.Servo(pin);
    }

    /**
     * translates the speed into a pulse length so we can control the servos
     *
     * @param speed   float which displays the speed from 1 to -1 where 1 is forward and -1 is backward
     * @param reverse boolean that reverses the number
     * @return integer that contains the pulse length in micro seconds
     */
    private int speedToPulseLength(float speed, boolean reverse) {
        int pulseLength = BASEPULSEWIDTH;
        if (speed > 1.0F) {
            speed = 1.0F;
        }
        if (speed < -1.0F) {
            speed = -1.0F;
        }
        if (reverse) {
            pulseLength -= Math.round(speed * MAXPULSEWIDTH);
        } else {
            pulseLength += Math.round(speed * MAXPULSEWIDTH);
        }
        return pulseLength;
    }

    /**
     * gets called every iteration that sets the speed of the servo
     *
     * @param speed   in float 1 is ful forward, -1 is backwards
     * @param reverse
     */
    public void update(float speed,boolean reverse) {
        servo.update(speedToPulseLength(speed,reverse));
    }

}
