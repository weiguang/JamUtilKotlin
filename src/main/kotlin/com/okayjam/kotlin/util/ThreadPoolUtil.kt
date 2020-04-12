package com.okayjam.kotlin.util

import org.slf4j.LoggerFactory
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class ThreadPoolUtil {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(ThreadPoolUtil::class.java)
        val AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors()


        /**
         * 获取线程池方法
         * @param poolName 线程池名称
         * @return 返回线程池，默认线程数 = 核心数 * 2
         */
        @JvmStatic
        fun getThreadPool(poolName: String?): ThreadPoolExecutor {
            val threadNumber = AVAILABLE_PROCESSORS * 2
            return getThreadPool(poolName, threadNumber)
        }

        /**
         * 获取线程池方法
         * @return 返回线程池
         */
        @JvmStatic
        fun getThreadPool(poolName: String?, threadNumber: Int): ThreadPoolExecutor {
            // 建立线程名字
            val namedThreadFactory: ThreadFactory = MyThreadFactory(poolName)
            LOGGER.info("New Thread pool max thread size:{}", threadNumber)
            return ThreadPoolExecutor(
                threadNumber, threadNumber,
                60L, TimeUnit.SECONDS,
                LinkedBlockingQueue(), namedThreadFactory
            )
        }

        /**
         * 关闭线程池
         * @param pool 线程池
         */
        @JvmStatic
        fun poolStopAndWait(pool: ExecutorService) { // 关闭线程池
            pool.shutdown()
            //判断线程池是否结束
            while (!pool.isTerminated) {
                try { // 等待30分钟
                    pool.awaitTermination(5, TimeUnit.MINUTES)
                } catch (e: InterruptedException) {
                    LOGGER.error("Pool wait for stop error,InterruptedException", e)
                } catch (e: Exception) {
                    LOGGER.error("Thread error：", e)
                }
            }
        }


        /**
         * 定义线程工厂类，实现自定义名称
         * 实现参考： Executors.defaultThreadFactory()
         */
        internal class MyThreadFactory(namePrefix: String?) : ThreadFactory {
            private val threadNumber = AtomicInteger(1)
            private val namePrefix: String = "$namePrefix-pool-"
            override fun newThread(r: Runnable): Thread {
                val t = Thread(r, namePrefix + threadNumber.getAndIncrement())
                if (t.isDaemon) t.isDaemon = false
                if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                return t
            }
        }
    }



}