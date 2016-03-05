package dodoichi.timer;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

/**
 * StopWatch provides simple API for timings.
 *
 * <p>At first, you must call {@link #start()}.
 * Next, you can call {@link #suspend()} to suspend or {@link #stop()} to end.</p>
 *
 * @author dodoichi
 */
public class StopWatch {

    private Duration totalDuration;
    private Instant startedAt;
    private Instant suspendedAt;
    private Instant resumedAt;

    public StopWatch() {
        totalDuration = Duration.ZERO;
    }

    /**
     * Start the stopwatch.
     * If this stopwatch has been started, {@link IllegalStateException} will be thrown.
     *
     * @throws IllegalStateException if the stopwatch has been started.
     */
    public void start() {
        if (totalDuration.compareTo(Duration.ZERO) > 0) {
            throw new IllegalStateException("already started");
        }
        // store current instant.
        startedAt = Instant.now();
        System.out.printf("starting at %s%n",startedAt);
    }

    /**
     * Stop the stopwatch.
     * If it has not started, {@link IllegalStateException} will be thrown.
     */
    public void stop() {
        if (startedAt == null) {
            throw new IllegalStateException("not started");
        }

        Instant i4 = Instant.now();
        Duration lastDuration;
        if (resumedAt != null) {
            lastDuration = Duration.between(resumedAt, i4);
        } else {
            lastDuration = Duration.between(startedAt, i4);
        }
        totalDuration = totalDuration.plus(lastDuration);
        System.out.printf("stopped at %s. total duration is %s%n", i4, totalDuration);
    }

    /**
     * Suspend the stopwatch.
     * if it has been already suspended, {@link IllegalStateException} will be thrown.
     *
     * @throws IllegalStateException if the stopwatch has not been started, or been suspended.
     */
    public void suspend() {
        if (startedAt == null) {
            throw new IllegalStateException("not started");
        }
        if (suspendedAt != null) {
            throw new IllegalStateException("already suspended.");
        }
        suspendedAt = Instant.now();
        totalDuration = totalDuration.plus(Duration.between(startedAt, suspendedAt));
        System.out.printf("suspended at %s. current duration is %s%n", suspendedAt, totalDuration);
    }

    /**
     * Resume the stopwatch from suspended state. if not suspended throw exception.
     * Suspended state will be cleared after resumed.
     *
     * @throws IllegalStateException if the stopwatch has not been suspended.
     */
    public void resume() {
        if (suspendedAt == null) {
            throw new IllegalStateException("not suspended");
        }

        resumedAt = Instant.now();
        Duration suspendedDuration = Duration.between(suspendedAt, resumedAt);
        suspendedAt = null;
        resumedAt = Instant.now();
        System.out.printf("during %s from suspended. now resuming at %s%n",
                suspendedDuration, resumedAt);
    }

    /**
     * Reset the stopwatch.
     */
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
