package com.zyd.timingwheel.timeout;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import com.zyd.timingwheel.task.impl.TimerTask;
import com.zyd.timingwheel.timeout.impl.Timeout;
import com.zyd.timingwheel.timer.HashedWheelBucket;
import com.zyd.timingwheel.timer.impl.Timer;
import com.zyd.timingwheel.timer.impl.TimerWheel;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class HashedWheelTimeout implements Timeout{

    static final InternalLogger logger =
            InternalLoggerFactory.getInstance(HashedWheelTimeout.class);
    private static final int ST_INIT = 0;
    public static final int ST_CANCELLED = 1;
    private static final int ST_EXPIRED = 2;
    private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER;

    static {
        AtomicIntegerFieldUpdater<HashedWheelTimeout> updater =
                PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimeout.class, "state");
        if (updater == null) {
            updater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
        }
        STATE_UPDATER = updater;
    }

    private final TimerWheel timer;
    private final TimerTask task;
    public final long deadline;

    @SuppressWarnings({"unused", "FieldMayBeFinal", "RedundantFieldInitialization" })
    private volatile int state = ST_INIT;

    // remainingRounds will be calculated and set by Worker.transferTimeoutsToBuckets() before the
    // HashedWheelTimeout will be added to the correct HashedWheelBucket.
    public long remainingRounds;
    String argv;

    // This will be used to chain timeouts in HashedWheelTimerBucket via a double-linked-list.
    // As only the workerThread will act on it there is no need for synchronization / volatile.
    public HashedWheelTimeout next;
    public HashedWheelTimeout prev;

    // The bucket to which the timeout was added
    public HashedWheelBucket bucket;

    public HashedWheelTimeout(TimerWheel timer, TimerTask task, long deadline, String argv) {
        this.timer = timer;
        this.task = task;
        this.deadline = deadline;
        this.argv = argv;

    }

    @Override
    public Timer timer() {
        return timer;
    }

    @Override
    public TimerTask task() {
        return task;
    }

    @Override
    public boolean cancel() {
        // only update the state it will be removed from HashedWheelBucket on next tick.
        if (!compareAndSetState(ST_INIT, ST_CANCELLED)) {
            return false;
        }
        // If a task should be canceled we put this to another queue which will be processed on each tick.
        // So this means that we will have a GC latency of max. 1 tick duration which is good enough. This way
        // we can make again use of our MpscLinkedQueue and so minimize the locking / overhead as much as possible.
        timer.cancelledTimeouts.add(this);
        return true;
    }

    public void remove() {
        HashedWheelBucket bucket = this.bucket;
        if (bucket != null) {
            bucket.remove(this);
        }
    }

    public boolean compareAndSetState(int expected, int state) {
        return STATE_UPDATER.compareAndSet(this, expected, state);
    }

    public int state() {
        return state;
    }

    @Override
    public boolean isCancelled() {
        return state() == ST_CANCELLED;
    }

    @Override
    public boolean isExpired() {
        return state() == ST_EXPIRED;
    }

    public void expire() {
        if (!compareAndSetState(ST_INIT, ST_EXPIRED)) {
            return;
        }

        try {
            task.run(this, argv);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', t);
            }
        }
    }

    @Override
    public String toString() {
        final long currentTime = System.nanoTime();
        long remaining = deadline - currentTime + timer.startTime;

        StringBuilder buf = new StringBuilder(192)
                .append(StringUtil.simpleClassName(this))
                .append('(')
                .append("deadline: ");
        if (remaining > 0) {
            buf.append(remaining)
                    .append(" ns later");
        } else if (remaining < 0) {
            buf.append(-remaining)
                    .append(" ns ago");
        } else {
            buf.append("now");
        }

        if (isCancelled()) {
            buf.append(", cancelled");
        }

        return buf.append(", task: ")
                .append(task())
                .append(')')
                .toString();
    }
}
