package com.zyd.timingwheel.timer.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.zyd.timingwheel.task.impl.TimerTask;
import com.zyd.timingwheel.timeout.impl.Timeout;

/**
 * ʱ���ֵĵײ�ṹ�����������ʱ��Ƭ
 * @author leshu
 *
 */
public interface Timer {
    Timeout newTimeout(TimerTask task, long delay, TimeUnit unit, String argv);
    Set<Timeout> stop();

}
