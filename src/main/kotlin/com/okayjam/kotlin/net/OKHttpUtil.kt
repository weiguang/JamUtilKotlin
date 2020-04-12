package com.okayjam.kotlin.net

import com.alibaba.fastjson.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

/**
 *
 *
 * @author Chen weiguang chen2621978@gmail.com
 *
 * @date 2020/04/08 14:46
 **/
class OKHttpUtil {
    companion object{
        val LOGGER: Logger = LoggerFactory.getLogger(OKHttpUtil::class.java)
        private const val HTTP_REQUEST_TIMEOUT = 10L
        const val HTTP_REQUEST_METHOD_GET = "GET"
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()

        private var client: OkHttpClient

        init {
            val builder:OkHttpClient.Builder =  OkHttpClient.Builder()
            builder.readTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            builder.connectTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            builder.followRedirects(true)
            client =  builder.build()
        }

        @JvmStatic
        fun getClient(): OkHttpClient? {
            return client
        }

        @JvmStatic
        @Throws(IOException::class)
        fun getConnection(url: String?, requestMethod: String?, headers: String?, params: String?): Call? {
            if (url == null) {
                return null;
            }
            val reqBuilder = Request.Builder().url(url)
            if (headers != null) {
                val header1: JSONObject = JSONObject.parseObject(headers)
                header1.keys.forEach(Consumer { re: String? -> reqBuilder.addHeader(re!!, header1.getString(re)) })
            }

            var body: RequestBody? = params?.toRequestBody(JSON) ?: ByteArray(0).toRequestBody(null)

            // set default method
            var requestMethod1 : String? = null
            if (requestMethod == null || HTTP_REQUEST_METHOD_GET == requestMethod) {
                requestMethod1 = HTTP_REQUEST_METHOD_GET
                body = null
            }

            reqBuilder.method(requestMethod1!!, body)
            return getClient()!!.newCall(reqBuilder.build())
        }

        @JvmStatic
        @Throws(IOException::class)
        fun requert(url: String?, method: String?, headers: String?, params: String?): Response? {
          return  getConnection(url, method, headers, params)?.execute()
        }

        @JvmStatic
        @Throws(IOException::class)
        fun requertAsync(url: String?, method: String?, headers: String?, params: String?) {
            val conn = getConnection(url, method, headers, params)
            conn!!.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LOGGER.info("get failed")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    LOGGER.info("Response:" + response.body?.string())
                }
            })
        }

    }



}