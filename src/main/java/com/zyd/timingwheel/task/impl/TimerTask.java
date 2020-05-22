package com.zyd.timingwheel.task.impl;

import com.zyd.timingwheel.timeout.impl.Timeout;

/**
 * 真正的任务对象
 * @author leshu
 *
 */
public interface TimerTask {

	void run(Timeout timeout, String argv) throws Exception;

}
