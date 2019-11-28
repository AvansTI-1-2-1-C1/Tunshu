package Utils;

import TI.Timer;

public class OnOffTimer extends Timer {

    private boolean enabled;

    public OnOffTimer(int interval) {
        super(interval);

        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            mark();
        }
    }

    @Override
    public boolean timeout() {
        if (this.enabled) {
            return super.timeout();
        } else {
            return false;
        }
    }
}
