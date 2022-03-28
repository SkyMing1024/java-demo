package com.sky.thread.book_example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockBasedSeqGenerator {
    private short sequence = -1;
    private final Lock lock = new ReentrantLock();

    public short nextSeq(){
        lock.lock();
        try {
            sequence = sequence>999 ? 0 : sequence++;
            return sequence;

        }finally {
            lock.unlock();
        }

    }
}
