package dodoichi.timer;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A countdown timer.
 *
 * @author dodoichi
 */
public class CountdownTimer {

    private AtomicLong totalMilliSeconds;
    private Instant startTime;

    public CountdownTimer() {
        totalMilliSeconds = new AtomicLong(0);
    }

    /**
     * Set how much time to wait.
     * If total milliseconds becomes minus, the timer throws {@link IllegalArgumentException}.
     *
     * @param hours
     *            long value of hours
     * @param minutes
     *            long value of minutes
     * @param seconds
     *            long value of seconds
     *
     * @throws IllegalArgumentException
     *             if total time is minus value.
     */
    public void set(long hours, long minutes, long seconds) {
        Duration total = Duration.ofHours(hours)
                .plus(Duration.ofMinutes(minutes)
                        .plus(Duration.ofSeconds(seconds)));
        long millis = total.toMillis();
        if (millis < 0) {
            throw new IllegalArgumentException("couldn't set minus value");
        }
        totalMilliSeconds = new AtomicLong(millis);
    }

    public Object getTime() {
        return totalMilliSeconds.get();
    }

    /**
     * Start this timer.
     * Throw {@link IllegalStateException} if the timer is set ZERO.
     *
     * @return ZERO if ends normally
     * @throws IllegalStateException
     *             if the timer is set ZERO.
     */
    public Long start() {
        if (totalMilliSeconds.get() == 0L) {
            throw new IllegalStateException("Timer is set ZERO");
        }

        startTime = Instant.now();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<Long> future = executor.schedule(() -> getDuration(),
                totalMilliSeconds.get(),
                TimeUnit.MILLISECONDS);

        executor.shutdown();

        try {
            return future.get();
        } catch (Exception e) {
            return null;
        }
    }

    private Long getDuration() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Duration duration = Duration.between(startTime, Instant.now());
        return totalMilliSeconds.get() - (duration.get(ChronoUnit.SECONDS) * 1000);
    }
}
