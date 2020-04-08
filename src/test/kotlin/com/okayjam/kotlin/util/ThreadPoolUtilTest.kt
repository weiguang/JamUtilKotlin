package com.okayjam.kotlin.util

import org.junit.Before
import org.junit.Test

class ThreadPoolUtilTest {

    @Before
    fun setUp() {
    }

    @Test
    fun t() {
        val threadPool = ThreadPoolUtil.getThreadPool("Jam")
        var s = System.currentTimeMillis();
        repeat(24) {
            threadPool.execute { Thread.sleep(1000) }
        }
        ThreadPoolUtil.poolStopAndWait(threadPool)

        println(System.currentTimeMillis() - s)

    }
}