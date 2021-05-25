package com.cc.learn.netty.timer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/04/08
 */
public class HashedWheelTimerTest {
    public static void main(String[] args) {
        Timer timer = new HashedWheelTimer();
        Timeout timeout1 = timer.newTimeout(timeout -> System.out.println(Thread.currentThread().getName()+" timeout1: " + LocalDateTime.now()), 3, TimeUnit.SECONDS);
        if (!timeout1.isExpired())
            timeout1.cancel();
        timer.newTimeout(timeout -> {
            System.out.println(Thread.currentThread().getName()+" timeout2: "+LocalDateTime.now());
            Thread.sleep(5000);
        },1,TimeUnit.SECONDS);
        timer.newTimeout(timeout -> System.out.println(Thread.currentThread().getName()+" timeout3: "+LocalDateTime.now()),3,TimeUnit.SECONDS);
        //停止所有任务
        //timer.stop();
    }
}
