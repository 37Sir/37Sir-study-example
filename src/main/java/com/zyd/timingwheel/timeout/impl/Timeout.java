package com.zyd.timingwheel.timeout.impl;

import com.zyd.timingwheel.task.impl.TimerTask;
import com.zyd.timingwheel.timer.impl.Timer;

/**
 * �����װ����
 * @author leshu
 *
 */
public interface Timeout {
    Timer timer();
    TimerTask task();
    boolean isExpired();
    boolean isCancelled();
    boolean cancel();
}
