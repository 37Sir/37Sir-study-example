package com.zyd.timingwheel.task;

import com.zyd.timingwheel.task.impl.TimerTask;
import com.zyd.timingwheel.timeout.impl.Timeout;

public class Task implements TimerTask{
    @Override
    public void run(Timeout timeout, String argv) throws Exception {
        System.out.println("timeout, argv = " + argv );
    }
}
