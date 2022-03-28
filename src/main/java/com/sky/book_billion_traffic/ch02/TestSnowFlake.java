package com.sky.book_billion_traffic.ch02;

import com.sky.book_billion_traffic.ch02.SnowFlake;

public class TestSnowFlake {
	//测试1秒能够生成的id个数
	public static void generateIdsInOneSecond() {
		 SnowFlake idWorker = new SnowFlake(1, 1);
		 long start = System.currentTimeMillis();
		 int i = 0;
		 for (; System.currentTimeMillis() - start <1000; i++) {
			 long l = idWorker.nextId();
			 System.out.println(Long.toBinaryString(l));

		 }
		 long end = System.currentTimeMillis();
		 System.out.println("耗时："+ (end - start));
		 System.out.println("生成id个数："+ i);
	}

	public static void main(String[] args) {
		 generateIdsInOneSecond();
}
}