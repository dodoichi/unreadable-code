package dodoichi.timer;

import java.util.concurrent.TimeUnit;

/**
 * A countdown timer.
 *
 * @author dodoichi
 */
public class CountdownTimer {

    private long totalMilliSeconds = 0;

    private long timePerUnit;
    private TimeUnit unit;

    /**
     * timePerUnit represents dependent on {@link TimeUnit}.
     * For example, if 10L and {@link TimeUnit#SECONDS} are passed,
     * 10L means "Ten seconds".
     *
     * @param timePerUnit the amount of time.
     * @param unit One of {@link TimeUnit} values.
     */
    public CountdownTimer(long timePerUnit, TimeUnit unit) {
        this.timePerUnit = timePerUnit;
        this.unit = unit;
    }

    /**
     * Set how much time to wait.
     *
     * @param milliSeconds the unit is always milliSec.
     */
    public void addMilliSeconds(long milliSeconds) {
        this.totalMilliSeconds = milliSeconds;
    }

    /**
     * Wait until countdown reaches 0.
     *
     * @throws InterruptedException if {@link TimeUnit#timedWait(Object, long)}
     *                              throws {@link InterruptedException}.
     */
    public synchronized void countdown() throws InterruptedException {
        while (totalMilliSeconds > 0) {
            long minus = unit.convert(timePerUnit, unit);
            unit.timedWait(this, minus);
            totalMilliSeconds -= minus;
            System.out.println(totalMilliSeconds / 1000);
        }
    }

}
