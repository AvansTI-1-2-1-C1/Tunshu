package Utils;

import java.time.LocalDateTime;

public class IntervalTimer {

    private LocalDateTime startLocalDateTime;

    public IntervalTimer() {

        this.startLocalDateTime = LocalDateTime.now();

    }

    public int timePassed(){

        LocalDateTime tempTime = LocalDateTime.now();
        return tempTime.getNano() - startLocalDateTime.getNano();

    }

    public void restart(){

        this.startLocalDateTime = LocalDateTime.now();

    }
}
