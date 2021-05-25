package com.cc.learn.netty.timer;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2021/04/07
 */
public class TimerTest {
    public static void main(String[] args) {
        timerDemo();
    }

    private static void timerDemo(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalDateTime.now()+" task");
            }
        }, 2000, 5000);
    }
}
