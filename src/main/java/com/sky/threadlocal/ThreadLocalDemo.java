package com.sky.threadlocal;

public class ThreadLocalDemo {

    /**
     * 模拟转账
     */

    static class Bank {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {

            @Override
            protected Integer initialValue() {
                return 0;
            }
        };

        public Integer get() {
            return threadLocal.get();
        }

        public void set(Integer money) {
            threadLocal.set(threadLocal.get()+money);
        }
    }

    static class Transfer implements Runnable {

        private Bank bank;

        public Transfer(Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                bank.set(10);
                System.out.println(Thread.currentThread().getName() + "余额" + bank.get());
            }
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        Transfer transfer = new Transfer(bank);
        Thread t1 = new Thread(transfer,"客户1");
        Thread t2 = new Thread(transfer,"客户2");

        t1.start();
        t2.start();
    }
}
