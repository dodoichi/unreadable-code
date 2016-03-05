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

    private Duration totalDuration;
    private Instant startedAt;
    private Instant suspendedAt;
    private Instant resumedAt;

    public StopWatch() {
        // initialize d.
        totalDuration = Duration.ZERO;
    }

    public void start() {
        // ensure this stop watch has not started.
        if (totalDuration.compareTo(Duration.ZERO) > 0) {
            throw new IllegalStateException("already started");
        }
        // store current instant.
        startedAt = Instant.now();
        System.out.printf("starting at %s%n",startedAt);
    }

    public void stop() {
        // if not started throw exception.
        if (startedAt == null) {
            throw new IllegalStateException("not started");
        }
        Instant i4 = Instant.now();
        Duration tmp = resumedAt != null ? Duration.between(resumedAt, i4) : Duration.between(startedAt, i4);
        totalDuration = totalDuration.plus(tmp);
        System.out.printf("stopped at %s. total duration is %s%n", i4, totalDuration);
    }

    public void suspend() {
        if (startedAt == null) {
            throw new IllegalStateException("not started");
        }
        // if already suspended throw exception.
        if (suspendedAt != null) {
            throw new IllegalStateException("already suspended.");
        }
        // suspend stop watch. save duration since start timing.
        suspendedAt = Instant.now();
        totalDuration = totalDuration.plus(Duration.between(startedAt, suspendedAt));
        System.out.printf("suspended at %s. current duration is %s%n", suspendedAt, totalDuration);
    }

    public void resume() {
        // resume suspended state. if not suspended throw exception.
        if (suspendedAt == null) {
            throw new IllegalStateException("not suspended");
        }
        resumedAt = Instant.now();
        Duration suspendedDuration = Duration.between(suspendedAt, resumedAt);
        // clear last suspended state.
        suspendedAt = null;
        resumedAt = Instant.now();
        System.out.printf("during %s from suspended. now resuming at %s%n", suspendedDuration, resumedAt);
    }

    public void reset() {
        // reset all state.
        startedAt = null;
        suspendedAt = null;
        resumedAt = null;
        totalDuration = Duration.ZERO;
    }

    public Temporal getStartTime() {
        return startedAt;
    }

    public Duration getDuration() {
        return totalDuration;
    }

}
