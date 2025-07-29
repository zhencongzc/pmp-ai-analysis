package com.pmp.infrastructure.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池工具类，提供多种场景的线程池配置和管理
 */
public class ThreadPoolUtil {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);

    // 核心线程数：默认 CPU 核心数 + 1
    private static final int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    // 最大线程数：默认 CPU 核心数 * 2 + 1
    private static final int DEFAULT_MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;
    // 队列容量：默认 1024
    private static final int DEFAULT_QUEUE_CAPACITY = 1024;
    // 线程空闲时间：默认 60 秒
    private static final long DEFAULT_KEEP_ALIVE_TIME = 60L;

    // 存储线程池的 Map
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();

    // 私有构造函数，防止实例化
    private ThreadPoolUtil() {
    }

    /**
     * 创建固定大小的线程池
     *
     * @param poolName 线程池名称
     * @param poolSize 线程池大小
     * @return 线程池实例
     */
    public static ExecutorService createFixedThreadPool(String poolName, int poolSize) {
        return createThreadPool(poolName, poolSize, poolSize, 0L,
                new LinkedBlockingQueue<>());
    }

    /**
     * 创建缓存线程池
     *
     * @param poolName 线程池名称
     * @return 线程池实例
     */
    public static ExecutorService createCachedThreadPool(String poolName) {
        return createThreadPool(poolName, 0, Integer.MAX_VALUE, 60L,
                new SynchronousQueue<>());
    }

    /**
     * 创建单线程线程池
     *
     * @param poolName 线程池名称
     * @return 线程池实例
     */
    public static ExecutorService createSingleThreadExecutor(String poolName) {
        return createThreadPool(poolName, 1, 1, 0L,
                new LinkedBlockingQueue<>());
    }

    /**
     * 创建定时任务线程池
     *
     * @param poolName     线程池名称
     * @param corePoolSize 核心线程数
     * @return 定时任务线程池实例
     */
    public static ScheduledExecutorService createScheduledThreadPool(String poolName, int corePoolSize) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName + "-%d")
                .setUncaughtExceptionHandler((t, e) -> log.error("Thread {} caught exception", t.getName(), e))
                .build();

        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(
                corePoolSize,
                threadFactory,
                new CustomRejectedExecutionHandler()
        );

        THREAD_POOLS.put(poolName, executor);
        log.info("Created scheduled thread pool: {}", poolName);
        return executor;
    }

    /**
     * 创建自定义线程池
     *
     * @param poolName        线程池名称
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程空闲时间
     * @param workQueue       工作队列
     * @return 线程池实例
     */
    public static ExecutorService createThreadPool(String poolName,
                                                   int corePoolSize,
                                                   int maximumPoolSize,
                                                   long keepAliveTime,
                                                   BlockingQueue<Runnable> workQueue) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName + "-%d")
                .setUncaughtExceptionHandler((t, e) -> log.error("Thread {} caught exception", t.getName(), e))
                .build();

        ExecutorService executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue,
                threadFactory,
                new CustomRejectedExecutionHandler()
        );

        THREAD_POOLS.put(poolName, executor);
        log.info("Created thread pool: {}", poolName);
        return executor;
    }

    /**
     * 获取默认配置的线程池
     *
     * @param poolName 线程池名称
     * @return 线程池实例
     */
    public static ExecutorService getDefaultThreadPool(String poolName) {
        return THREAD_POOLS.computeIfAbsent(poolName, name ->
                createThreadPool(
                        name,
                        DEFAULT_CORE_POOL_SIZE,
                        DEFAULT_MAX_POOL_SIZE,
                        DEFAULT_KEEP_ALIVE_TIME,
                        new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY)
                )
        );
    }

    /**
     * 获取 IO 密集型任务的线程池（线程数为 CPU 核心数 * 2）
     *
     * @param poolName 线程池名称
     * @return 线程池实例
     */
    public static ExecutorService getIOIntensiveThreadPool(String poolName) {
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        return THREAD_POOLS.computeIfAbsent(poolName, name ->
                createThreadPool(
                        name,
                        poolSize,
                        poolSize,
                        DEFAULT_KEEP_ALIVE_TIME,
                        new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY)
                )
        );
    }

    /**
     * 获取 CPU 密集型任务的线程池（线程数为 CPU 核心数 + 1）
     *
     * @param poolName 线程池名称
     * @return 线程池实例
     */
    public static ExecutorService getCPUIntensiveThreadPool(String poolName) {
        int poolSize = Runtime.getRuntime().availableProcessors() + 1;
        return THREAD_POOLS.computeIfAbsent(poolName, name ->
                createThreadPool(
                        name,
                        poolSize,
                        poolSize,
                        DEFAULT_KEEP_ALIVE_TIME,
                        new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY)
                )
        );
    }

    /**
     * 优雅关闭所有线程池
     */
    public static void shutdownAll() {
        THREAD_POOLS.forEach((name, executor) -> {
            shutdown(executor, name);
        });
        THREAD_POOLS.clear();
    }

    /**
     * 优雅关闭指定线程池
     *
     * @param executor 线程池实例
     * @param poolName 线程池名称
     */
    public static void shutdown(ExecutorService executor, String poolName) {
        if (executor == null || executor.isShutdown()) {
            return;
        }

        try {
            // 拒绝新任务
            executor.shutdown();
            log.info("Shutting down thread pool: {}", poolName);

            // 等待未完成任务结束
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                // 超时后强制关闭
                executor.shutdownNow();
                log.warn("Timeout waiting for tasks to complete, forcing shutdown: {}", poolName);

                // 再次等待任务终止
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    log.error("Failed to shutdown thread pool: {}", poolName);
                }
            }
        } catch (InterruptedException e) {
            // 中断时强制关闭
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            log.error("Interrupted while shutting down thread pool: {}", poolName);
        } finally {
            THREAD_POOLS.remove(poolName);
        }
    }

    /**
     * 获取线程池状态信息
     *
     * @param executor 线程池实例
     * @return 状态信息字符串
     */
    public static String getStatus(ExecutorService executor) {
        if (executor == null || !(executor instanceof ThreadPoolExecutor)) {
            return "Unknown";
        }

        ThreadPoolExecutor tp = (ThreadPoolExecutor) executor;
        return String.format("PoolSize: %d, Active: %d, Completed: %d, Task: %d, QueueSize: %d",
                tp.getPoolSize(),
                tp.getActiveCount(),
                tp.getCompletedTaskCount(),
                tp.getTaskCount(),
                tp.getQueue().size());
    }

    /**
     * 自定义拒绝策略
     */
    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 记录日志
            log.error("Task rejected, pool: {}, PoolSize: {}, Active: {}, QueueSize: {}",
                    executor.toString(),
                    executor.getPoolSize(),
                    executor.getActiveCount(),
                    executor.getQueue().size());

            // 根据业务需求选择策略：
            // 1. 抛出异常
            throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + executor.toString());

            // 2. 由调用线程执行
            // r.run();

            // 3. 丢弃最老的任务
            // executor.getQueue().poll();
            // executor.execute(r);
        }
    }

    public static void main(String[] args) {
        // 获取默认线程池
        ExecutorService executor = ThreadPoolUtil.getDefaultThreadPool("default-pool");
        // 提交任务
        Future<?> future = executor.submit(() -> {
            // 任务逻辑
            throw new RuntimeException("任务执行失败");
        });
        try {
            future.get(); // 阻塞等待任务完成，若有异常会在此抛出
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause(); // 获取实际异常
            System.err.println("捕获到异常: " + cause.getMessage());
        }
        // 关闭线程池
        ThreadPoolUtil.shutdown(executor, "default-pool");
        // 或关闭所有线程池
        ThreadPoolUtil.shutdownAll();
    }
}    