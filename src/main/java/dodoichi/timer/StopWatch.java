package dodoichi.timer;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

/**
 * StopWatch.
 *
 * @author dodoichi
 */
public class StopWatch {

    private Duration d;
    private Instant i1;
    private Instant i2;
    private Instant i3;

    public StopWatch() {
        // initialize d.
        d = Duration.ZERO;
    }

    public void start() {
        // ensure this stop watch has not started.
        if (d.compareTo(Duration.ZERO) > 0) {
            throw new IllegalStateException("already started");
        }
        // store current instant.
        i1 = Instant.now();
        System.out.printf("starting at %s%n", i1);
    }

    public void stop() {
        // if not started throw exception.
        if (i1 == null) {
            throw new IllegalStateException("not started");
        }
        Instant i4 = Instant.now();
        Duration tmp = i3 != null ? Duration.between(i3, i4) : Duration.between(i1, i4);
        d = d.plus(tmp);
        System.out.printf("stopped at %s. total duration is %s%n", i4, d);
    }

    public void suspend() {
        if (i1 == null) {
            throw new IllegalStateException("not started");
        }
        // if already suspended throw exception.
        if (i2 != null) {
            throw new IllegalStateException("already suspended.");
        }
        // suspend stop watch. save duration since start timing.
        i2 = Instant.now();
        d = d.plus(Duration.between(i1, i2));
        System.out.printf("suspended at %s. current duration is %s%n", i2, d);
    }

    public void resume() {
        // resume suspended state. if not suspended throw exception.
        if (i2 == null) {
            throw new IllegalStateException("not suspended");
        }
        i3 = Instant.now();
        Duration tmp = Duration.between(i2, i3);
        // clear last suspended state.
        i2 = null;
        i3 = Instant.now();
        System.out.printf("during %s from suspended. now resuming at %s%n", tmp, i3);
    }

    public void reset() {
        // reset all state.
        i1 = null;
        i2 = null;
        i3 = null;
        d = Duration.ZERO;
    }

    public Temporal getStartTime() {
        return i1;
    }

    public Duration getDuration() {
        return d;
    }

}
