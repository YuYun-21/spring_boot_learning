package com.yuyun.service.task;

import java.util.concurrent.*;

public class Destin {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                2,
                500,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread();
                        return thread;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy()
        );
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("哈哈哈！");
            }
        });

        Future<Object> submit = executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "返回值";
            }
        });
        submit.get();
    }
}
