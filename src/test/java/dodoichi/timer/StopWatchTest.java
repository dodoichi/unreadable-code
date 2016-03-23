package dodoichi.timer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.time.Duration;

/**
 * StopWatchTest.
 *
 * @author dodoichi
 */
public class StopWatchTest {

    @Test
    public void durationIsZeroBeforeStart() {
        StopWatch sw = new StopWatch();
        assertThat("duration is zero because stop watch has not been started",
                sw.getDuration(),
                equalTo(Duration.ZERO));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotStartTwice() {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.start();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotStopBeforeStart() {
        StopWatch sw = new StopWatch();
        sw.stop();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotSuspendBeforeStart() {
        StopWatch sw = new StopWatch();
        sw.suspend();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotSuspendDuraingSuspending() {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.suspend();
        sw.suspend();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotResumeBeforeStart() {
        StopWatch sw = new StopWatch();
        sw.resume();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotResumeBeforeSuspend() {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.resume();
    }

    @Test(expected = IllegalStateException.class)
    public void cannotGetStartTimeBeforeStart() {
        StopWatch sw = new StopWatch();
        sw.getStartTime();
    }

}
