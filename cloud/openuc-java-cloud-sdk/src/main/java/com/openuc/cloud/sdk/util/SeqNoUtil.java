package com.openuc.cloud.sdk.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangshun
 */
public class SeqNoUtil {

    private static AtomicInteger seqNoSender = new AtomicInteger(0);

    /**
     * 获取一个序列号
     * @return
     */
    public static int getSeqNo() {
        int result = seqNoSender.incrementAndGet();
        if (result == Integer.MAX_VALUE) {
            seqNoSender.set(0);
        }

        return result;
    }
}
