import TI.BoeBot;

public class Lights {
    public static void PoliceWagon() {

        boolean color1 = true;
        while (true) {
            if (color1) {
                BoeBot.rgbSet(0, 255, 0, 0);
                BoeBot.rgbSet(2, 0, 0, 255);
            } else {
                BoeBot.rgbSet(0, 0, 0, 255);
                BoeBot.rgbSet(2, 255, 0, 0);
            }
            BoeBot.rgbShow();
            color1 = !color1;
            BoeBot.wait(200);
        }

    }

    public static void alert() {

        boolean colorOn = true;
        while (true) {
            if (colorOn) {
                BoeBot.rgbSet(0, 255, 255, 0);
                BoeBot.rgbSet(1, 255, 255, 0);
                BoeBot.rgbSet(2, 255, 255, 0);
                BoeBot.rgbSet(3, 255, 255, 0);
                BoeBot.rgbSet(4, 255, 255, 0);
                BoeBot.rgbSet(5, 255, 255, 0);
            } else {
                BoeBot.rgbSet(0, 0, 0, 0);
                BoeBot.rgbSet(1, 0, 0, 0);
                BoeBot.rgbSet(2, 0, 0, 0);
                BoeBot.rgbSet(3, 0, 0, 0);
                BoeBot.rgbSet(4, 0, 0, 0);
                BoeBot.rgbSet(5, 0, 0, 0);
            }
            BoeBot.rgbShow();
            colorOn = !colorOn;
            BoeBot.wait(200);
        }

    }
}
