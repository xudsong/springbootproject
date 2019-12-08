package com.xudasong.service.springcloudservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExcutorTest {

    private static Integer page = 1;
    private static Boolean exeFlag = true;
    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (exeFlag) {
            if (page<=100) {

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            System.out.println(i);
                        }
                        page++;
                    }
                });
            }else {
                //活动线程为0时，关闭线程池
                if (((ThreadPoolExecutor)executorService).getActiveCount()==0){
                   executorService.shutdown();
                   exeFlag=false;
                   System.out.println("结束");
                }
            }
            try {
                //休眠0.1秒
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }

}
