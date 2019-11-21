package ApplicationLayer;

import TI.BoeBot;

public class Tunshu {

    public static void start() {
        init();

        /**
         * detection loop
         */
        while (true) {
            BoeBot.wait(0, 20000);
        }
    }

    /**
     * initialise all the classes and objects
     */
    public static void init(){

    }
}
