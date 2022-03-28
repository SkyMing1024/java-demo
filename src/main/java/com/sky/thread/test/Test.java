package com.sky.thread.test;

import com.sky.thread.p1_basic.TheadState;
import lombok.SneakyThrows;
import org.apache.poi.ss.formula.functions.T;

import javax.sound.midi.Track;
import java.util.Stack;

public class Test {

    public static void main(String[] args) {
        final VT vt = new VT();

        Thread Thread01 = new Thread(vt);
        Thread Thread02 = new Thread(new Runnable() {
            public void run() {
                try {
//                    Thread.sleep(3000);
                } catch (Exception ignore) {
                }
                vt.sign = true;
                vt.num = 2;
                System.out.println("vt.sign = true 通知 while (!sign) 结束！"+ vt.sign);
            }
        });

        Thread01.start();
        Thread02.start();
    }

}

class VT implements Runnable {

    public boolean sign = false;

    public int num = 1;

    @SneakyThrows
    public void run() {
        while (!sign) {
            int cnt = 3;
            for (int i = 0; i < cnt; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
        }
        System.out.println("你坏");
        System.out.println(num);

        Stack<String> stack = new Stack<>();
        stack.pop();
    }
}
