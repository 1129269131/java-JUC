package com.koala.jucsenior.cf;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * day06：
 *      CompletableFuture常用方法
 *
 * Create by koala on 2022-01-04
 */
public class CompletableFutureAPIDemo
{

    /**
     * 获得结果和触发计算：
     *      测试（1、2、3、4）：修改两个sleep值后执行查看运行结果
     */
    @Test
    public void m1() throws InterruptedException, ExecutionException
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            return 1;
        },threadPoolExecutor);

        //System.out.println(future.get());//1、不见不散
        //System.out.println(future.get(2L,TimeUnit.SECONDS));//2、过时不候

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        //System.out.println(future.getNow(9999));//3、立即获取结果不阻塞

        System.out.println(future.complete(-44)+"\t"+future.get());//4、是否打断get方法立即返回括号值


        threadPoolExecutor.shutdown();
    }

    /**
     * 对计算结果进行处理：thenApply
     */
    @Test
    public void m2_1()
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            System.out.println("-----1");
            int age = 10/0; // 异常情况：哪步出错就停在哪步
            return f + 2;
        }).thenApply(f -> {
            System.out.println("-----2");
            return f + 3;
        }).thenApply(f -> {
            System.out.println("-----3");
            return f + 4;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("----result: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        }).join());


        threadPoolExecutor.shutdown();
    }

    /**
     * 对计算结果进行处理：handle
     */
    @Test
    public void m2_2()
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 1;
        }).handle((f,e) -> {
            System.out.println("-----1");
            int age = 10/0; // 异常情况：有异常也可以往下一步走，根据带的异常参数可以进一步处理
            return f + 2;
        }).handle((f,e) -> {
            System.out.println("-----2");
            return f + 3;
        }).handle((f,e) -> {
            System.out.println("-----3");
            return f + 4;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("----result: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        }).join());


        threadPoolExecutor.shutdown();
    }

    /**
     * 对计算结果进行消费
     */
    @Test
    public void m3()
    {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f+2;
        }).thenApply(f -> {
            return f+3;
        }).thenAccept(r -> System.out.println(r));


        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join());


        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {}).join());


        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA + " resultB").join());
    }

    /**
     * 对计算速度进行选用
     */
    @Test
    public void m4()
    {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        }), r -> {
            return r;
        }).join());

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * 对计算结果进行合并：thenCombine
     */
    @Test
    public void m5()
    {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return 20;
        }), (r1, r2) -> {
            return r1 + r2;
        }).join());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        System.out.println(CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("0-------result: " + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        }).join());


        threadPoolExecutor.shutdown();
    }

}
