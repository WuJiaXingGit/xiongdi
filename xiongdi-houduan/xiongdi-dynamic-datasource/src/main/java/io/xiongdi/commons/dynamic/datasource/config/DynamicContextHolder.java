package io.xiongdi.commons.dynamic.datasource.config;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 多数据源上下文
 * @author wujiaxing
 * @date 2019-07-21
 */
public class DynamicContextHolder {

    /**
     * 这个本地线程变量维护着双端队列，可以装很多的数据源名
     */
    @SuppressWarnings("all")
    private final static ThreadLocal<Deque<String>> CONTEXT_HOLDER = new ThreadLocal<Deque<String>>() {
        @Override
        protected Deque<String> initialValue() {
            return new ArrayDeque<>();
        }
    };

    /**
     * 获取当前线程的数据源
     * @return
     */
    public static String peek() {
        return CONTEXT_HOLDER.get().peek();
    }

    /**
     * 设置当前线程的数据源
     * @param datasource
     */
    public static void push(String datasource) {
        CONTEXT_HOLDER.get().push(datasource);
    }

    /**
     * 清空当前线程的数据源
     */
    public static void poll() {
        Deque<String> deque = CONTEXT_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            CONTEXT_HOLDER.remove();
        }
    }
}
