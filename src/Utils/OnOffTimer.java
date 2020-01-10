package Utils;

import TI.Timer;

public class OnOffTimer extends Timer {

    private boolean enabled;

    /**
     * this timer is a child of the main timer class from the Boebot library
     * @param interval the interval time
     */
    public OnOffTimer(int interval) {
        super(interval);

        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * this timer extends on the normal timer in a way that the timer is able to be turned of and on
     * this will give more opportunities to use this in an efficient way
     * @param enabled set the state of the timer, enabled or disabled.
     */
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
