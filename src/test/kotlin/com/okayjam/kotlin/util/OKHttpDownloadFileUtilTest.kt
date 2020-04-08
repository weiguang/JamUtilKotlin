package com.okayjam.kotlin.util

import com.okayjam.kotlin.net.OKHttpUtil
import org.junit.Test
import java.io.IOException

class OKHttpDownloadFileUtilTest {

    @Test
    @Throws(IOException::class)
    fun download() {
        val url = "https://www.baidu.com/index.html"
        var start = System.currentTimeMillis();
        OKHttpDownloadFileUtil.download(url, "GET", null, "d:/1");
        println( System.currentTimeMillis() - start );
    }
}