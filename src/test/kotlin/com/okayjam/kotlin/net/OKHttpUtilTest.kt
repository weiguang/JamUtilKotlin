package com.okayjam.kotlin.net

import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * @author Chen weiguang chen2621978@gmail.com
 * @date 2020/04/08 15:04
 */
class OKHttpUtilTest {

    @Before
    fun setUp() {
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testRequest() {
        val url = "https://www.baidu.com"
        //        String url = "http://127.0.0.1/index.html";
        val response = OKHttpUtil.requert(url, OKHttpUtil.HTTP_REQUEST_METHOD_GET, null, null)
        println(" message:${response?.message}, body:${response?.body?.string()}")
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testRequestAsync() {
        val url = "https://www.baidu.com"
        //        String url = "http://127.0.0.1/index.html";
        OKHttpUtil.requertAsync(url, OKHttpUtil.HTTP_REQUEST_METHOD_GET, null, null)
        Thread.sleep(5000)
    }
}