package com.sky.threadlocal;

public class ThreadBankTest {
    static class Bank{
        int account;

        public Bank(int account) {
            this.account = account;
        }

        public int getAccount() {
            return account;
        }

        public void setAccount(int account) {
            this.account = account;
        }

        public void save(int money){
            this.account = this.getAccount()+money;
        }
    }

    static class Transfer implements Runnable{

        private Bank bank;

        public Transfer(Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
//                System.out.println(Thread.currentThread().getName()+"转账前余额："+bank.getAccount());
                bank.setAccount(bank.getAccount()+10);
                System.out.println(Thread.currentThread().getName()+"余额："+bank.getAccount());
            }
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank(0);
        Transfer transfer = new Transfer(bank);
        Thread t1 = new Thread(transfer,"客户1");
        Thread t2 = new Thread(transfer,"客户2");

        t1.start();
        t2.start();
    }
}
