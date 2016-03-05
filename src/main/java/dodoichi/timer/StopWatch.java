package dodoichi.timer;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

/**
 * StopWatch provides simple API for timings.
 *
 * <p>At first, you must call {@link #start()}.
 * Next, you can call {@link #suspend()} to suspend or {@link #stop()} to end.
 * If the stopwatch has been suspended, you can resume it.</p>
 *
 * @author dodoichi
 */
public class StopWatch {

    private Duration totalDuration;
    private Instant startedAt;
    private Instant originalStartedAt;
    private Instant suspendedAt;
    private Instant resumedAt;

    public StopWatch() {
        totalDuration = Duration.ZERO;
    }

    /**
     * Start the stopwatch.
     * If this stopwatch has been already started, {@link IllegalStateException} will be thrown.
     * You can start again if the stopwatch has been stopped.
     *
     * @throws IllegalStateException if the stopwatch has been already started.
     */
    public void start() {
        if (isStarted()) {
            throw new IllegalStateException("already started");
        }

        startedAt = Instant.now();
        originalStartedAt = Instant.from(startedAt);
        totalDuration = Duration.ZERO;
        System.out.printf("starting at %s%n", startedAt);
    }

    /**
     * Stop the stopwatch.
     * The stopwatch state must be started or suspended.
     * If it has not been started, {@link IllegalStateException} will be thrown.
     *
     * @throws IllegalStateException if the stopwatch has not been started.
     */
    public void stop() {
        if (!isStarted()) {
            throw new IllegalStateException("not started");
        }

        Instant stoppedAt = Instant.now();
        Duration lastDuration;
        if (isSuspended()) {
            lastDuration = Duration.between(startedAt, suspendedAt);
        } else {
            lastDuration = Duration.between(startedAt, stoppedAt);
        }
        totalDuration = totalDuration.plus(lastDuration);
        reset();
        System.out.printf("stopped at %s. total duration is %s%n", stoppedAt, totalDuration);
    }

    /**
     * Suspend the stopwatch.
     * if it has been already suspended, {@link IllegalStateException} will be thrown.
     *
     * @throws IllegalStateException if the stopwatch has not been started nor been suspended.
     */
    public void suspend() {
        if (!isStarted()) {
            throw new IllegalStateException("not started");
        } else if (isSuspended()) {
            throw new IllegalStateException("already suspended.");
        }

        suspendedAt = Instant.now();
        totalDuration = totalDuration.plus(Duration.between(startedAt, suspendedAt));
        System.out.printf("suspended at %s. current duration is %s%n", suspendedAt, totalDuration);
    }

    /**
     * Resume the stopwatch from suspended state.
     * if the stopwatch has not been suspended {@link IllegalStateException} will be thrown.
     * Suspended state will be cleared after resumed.
     *
     * @throws IllegalStateException if the stopwatch has not been suspended.
     */
    public void resume() {
        if (!isSuspended()) {
            throw new IllegalStateException("not suspended");
        }

        resumedAt = Instant.now();
        Duration suspendedDuration = Duration.between(suspendedAt, resumedAt);
        suspendedAt = null;
        startedAt = Instant.from(resumedAt);
        System.out.printf("during %s from suspended. now resuming at %s%n",
                suspendedDuration, resumedAt);
    }

    /**
     * Reset the stopwatch.
     */
    public void reset() {
        startedAt = null;
        suspendedAt = null;
        resumedAt = null;
    }

    /**
     * Return the timing when the stopwatch started.
     * If the stopwatch has not been started {@link IllegalStateException} will be thrown.
     *
     * @return the timing when the stopwatch started.
     * @throws IllegalStateException if the stopwatch has not been started yet.
     */
    public Temporal getStartTime() {
        if (originalStartedAt == null) {
            throw new IllegalStateException("not started");
        }
        return originalStartedAt;
    }

    /**
     * Return duration between the stopwatch started and current instant.
     * If the stopwatch has been suspended at least once, this method returns total running
     * duration. Or, if the stopwatch has never been started, returns ZERO.
     *
     * @return duration the stopwatch has been running so far.
     */
    public Duration getDuration() {
        Duration currentTotalDuration = totalDuration;
        if (isStarted() && (!isSuspended() || !isStopped())) {
            currentTotalDuration = totalDuration.plus(Duration.between(startedAt, Instant.now()));
        }
        return currentTotalDuration;
    }

    private boolean isStarted() {
        return startedAt != null;
    }

    private boolean isSuspended() {
        return suspendedAt != null;
    }

    private boolean isStopped() {
        return !totalDuration.equals(Duration.ZERO);
    }

}
