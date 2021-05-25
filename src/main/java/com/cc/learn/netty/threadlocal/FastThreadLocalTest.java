package com.cc.learn.netty.threadlocal;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

/**
*  FastThreadLocal InternalThreadLocalMap FastThreadLocalThread
 *  如果一个线程需要存多个数据，那就需要多个FastThreadLocal，
 *  一个FastThreadLocalThread绑定一个InternalThreadLocalMap,
 *  一个FastThreadLocal对应多个FastThreadLocalThread,
 *  UnpaddedInternalThreadLocalMap中维护了一个静态变量nextIndex和Object[]。
 *  FastThreadLocal维护了一个index，这个index初始化（UnpaddedInternalThreadLocalMap.nextIndex）之后就不会改变。
 *  index是InternalThreadLocalMap存放变量的位置。
 *  也就是说，一个线程中使用多个ThreadLocal存放变量，每个线程绑定一个ThreadlocalMap，每个Threadlocal做set的时候，存放的位置都是Threadlocal维护的index,
 *  这个index在多个ThreadLocalMap中是固定的，这也就是Netty设计的空间换时间的设计思路。
 *
**/
public class FastThreadLocalTest {

    private static final FastThreadLocal<String> THREAD_NAME_LOCAL = new FastThreadLocal<String>(){
        @Override
        protected String initialValue() throws Exception {
            return "hello,world";
        }
    };

    private static final FastThreadLocal<TradeOrder> TRADE_THREAD_LOCAL = new FastThreadLocal<>();

    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {

            int tradeId = i;

            String threadName = "thread-" + i;

            new FastThreadLocalThread(() -> {

                THREAD_NAME_LOCAL.set(threadName);
                THREAD_NAME_LOCAL.set("threadName");

                TradeOrder tradeOrder = new TradeOrder(tradeId, tradeId % 2 == 0 ? "已支付" : "未支付");

                TRADE_THREAD_LOCAL.set(tradeOrder);

                System.out.println("threadName: " + THREAD_NAME_LOCAL.get());
                System.out.println("threadName: " + THREAD_NAME_LOCAL.get());

                System.out.println("tradeOrder info：" + TRADE_THREAD_LOCAL.get());

            }, threadName).start();

            System.out.println(THREAD_NAME_LOCAL.get());

        }

    }

    static class TradeOrder {

        long id;

        String status;

        public TradeOrder(int id, String status) {

            this.id = id;

            this.status = status;

        }

        @Override

        public String toString() {

            return "id=" + id + ", status=" + status;

        }

    }

}
