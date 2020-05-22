package com.zyd.timingwheel.timer.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.zyd.timingwheel.task.impl.TimerTask;
import com.zyd.timingwheel.timeout.impl.Timeout;

/**
 * 时间轮的底层结构，用来存各个时间片
 * @author leshu
 *
 */
public interface Timer {
    Timeout newTimeout(TimerTask task, long delay, TimeUnit unit, String argv);
    Set<Timeout> stop();

}
