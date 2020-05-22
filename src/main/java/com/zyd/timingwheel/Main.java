package com.zyd.timingwheel;

import java.util.concurrent.TimeUnit;

import com.zyd.timingwheel.task.Task;
import com.zyd.timingwheel.task.impl.TimerTask;
import com.zyd.timingwheel.timer.impl.Timer;
import com.zyd.timingwheel.timer.impl.TimerWheel;

/**
 * ∆Ù∂Ø≤‚ ‘
 * @author leshu
 *
 */
public class Main {
    final static Timer timer = new TimerWheel();

    public static void main(String[] args) {
        TimerTask timerTask = new Task();
        for (int i = 0; i < 10; i++) {
            timer.newTimeout(timerTask, 5, TimeUnit.SECONDS, "" + i );
        }
    }
}
