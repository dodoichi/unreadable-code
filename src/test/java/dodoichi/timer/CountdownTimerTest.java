package dodoichi.timer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CountdownTimerTest {

    @Test
    public void defaultTimerIsSetZero() {
        assertThat("default timer has to be set 0 second.",
                (new CountdownTimer()).getTime(),
                equalTo(0L));
    }

    @Test
    public void setAndGetBeforeStart() {
        CountdownTimer timer = new CountdownTimer();
        timer.set(23, 59, 59);
        assertThat("normal set",
                timer.getTime(),
                equalTo(86399000L));

        timer.set(23, 59, 60);
        assertThat("exceeded seconds",
                timer.getTime(),
                equalTo(86400000L));

    }

    @Test(expected = IllegalArgumentException.class)
    public void setMinusThrowsException() {
        CountdownTimer timer = new CountdownTimer();
        timer.set(-1, 0, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void preventStartIfTimerIsZero() {
        CountdownTimer timer = new CountdownTimer();
        timer.start();
    }

    /*
     * start timer.
     * timer runs on new thread.
     * returns duration left.
     * if argument is null, timer throws NullPointerException.
     */
    @Test
    public void endNormally() {
        CountdownTimer timer = new CountdownTimer();
        timer.set(0, 0, 5);
        assertThat("returns ZERO",
                timer.start(),
                equalTo(0L));
    }

}
