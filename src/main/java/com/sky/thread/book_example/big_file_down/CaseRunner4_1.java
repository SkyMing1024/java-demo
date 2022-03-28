/*
授权声明：
本源码系《Java多线程编程实战指南（核心篇）》一书（ISBN：978-7-121-31065-2，以下称之为“原书”）的配套源码，
欲了解本代码的更多细节，请参考原书。
本代码仅为原书的配套说明之用，并不附带任何承诺（如质量保证和收益）。
以任何形式将本代码之部分或者全部用于营利性用途需经版权人书面同意。
将本代码之部分或者全部用于非营利性用途需要在代码中保留本声明。
任何对本代码的修改需在代码中以注释的形式注明修改人、修改时间以及修改内容。
本代码可以从以下网址下载：
https://github.com/Viscent/javamtia
http://www.broadview.com.cn/31065
*/
package com.sky.thread.book_example.big_file_down;

import com.sky.util.Debug;

public class CaseRunner4_1 {

  public static void main(String[] args) throws Exception {
    if (0 == args.length) {
      args = new String[] { "https://static001.geekbang.org/resource/image/4f/9f/4f88e49e08d5d41438a7d31a36e1a69f.png?x-oss-process=image/resize,w_334,h_334/format,webp", "2", "3" };
    }
    main0(args);
  }

  public static void main0(String[] args) throws Exception {
    final int argc = args.length;
    BigFileDownloader downloader = new BigFileDownloader(args[0]);

    // 下载线程数
    int workerThreadsCount = argc >= 2 ? Integer.valueOf(args[1]) : 2;
    long reportInterval = argc >= 3 ? Integer.valueOf(args[2]) : 2;

    Debug.info("downloading %s%nConfig:worker threads:%s,reportInterval:%s s.",
        args[0], workerThreadsCount, reportInterval);

    downloader.download(workerThreadsCount, reportInterval * 1000);
  }
}
