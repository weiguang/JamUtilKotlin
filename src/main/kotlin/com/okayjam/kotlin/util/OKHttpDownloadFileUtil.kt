package com.okayjam.kotlin.util

import com.okayjam.kotlin.net.OKHttpUtil
import okhttp3.Call
import org.apache.commons.io.FilenameUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OKHttpDownloadFileUtil {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(OKHttpDownloadFileUtil::class.java)

        @Throws(IOException::class)
        fun download(
            downloadUrl: String?,
            requestMethod: String?,
            params: String?,
            path: String?
        ): String? {
            return download(downloadUrl, requestMethod, params, null, path)
        }

        /**
         *  eg.    DownloadFileUtil.download(url, "POST", params, "d:\1\");
         * @param downloadUrl 下载地址
         * @param requestMethod 请求方式。 POST 或者 GET
         * @param params 如果是POST，还有请求参数，json格式
         * @param headers header参数，json格式
         * @param path 保存文件的目录（是目录，文件名会根据返回流得到）
         * @return 返回最终文件保存的路径
         * @throws IOException IO异常
         */
        @Throws(IOException::class)
        fun download(
            downloadUrl: String?,
            requestMethod: String?,
            params: String?,
            headers: String?,
            path: String?
        ): String? {
            val conn: Call? = OKHttpUtil.getConnection(downloadUrl, requestMethod, params, headers)
            val response = conn?.execute()
            val responseCode = response?.code
            if (responseCode != HttpURLConnection.HTTP_OK) {
                LOGGER.error("【下载导出文件】请求响应错误！返回状态码：${responseCode} ，错误信息：${response?.message}, ${response?.body?.string()} ")
                return null
            }
            // 获取文件名
            val field = response.header("Content-Disposition")
            var fileName: String
            if (field != null) {
                fileName = URLDecoder.decode(field, StandardCharsets.UTF_8.toString())
                fileName = fileName.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$".toRegex(), "$1")
            } else {
                fileName =
                    conn.request().url.toUrl().host + "-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(
                        LocalDateTime.now()
                    )
                val extension: String = FilenameUtils.getExtension(conn.request().url.toUrl().path)
                fileName += ".$extension"
            }
            // 读取输入流并保存
            val inStream: InputStream = BufferedInputStream(response.body?.byteStream())
            val fullPath = "$path/$fileName"
            saveToFile(inStream, fullPath)
            response.close()
            return fullPath
        }


        /**
         * 保存文件
         * @param inStream 流
         * @param path 保存文件的路径
         * @throws IOException IO
         */
        @Throws(IOException::class)
        fun saveToFile(inStream: InputStream, path: String?) {
            val fs = FileOutputStream(path)
            // 下载网络文件
            var byteread: Int
            val buffer = ByteArray(4096)
            while (inStream.read(buffer).also { byteread = it } != -1) {
                fs.write(buffer, 0, byteread)
            }
            fs.close()
        }
    }
}