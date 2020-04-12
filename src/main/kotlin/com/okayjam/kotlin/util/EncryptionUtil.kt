package com.okayjam.kotlin.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class EncryptionUtil private constructor() {
    companion object {
        /**
         * @Author Weiguang Chen(chen2621978@gmail.com) on 2017/7/15 17:21
         * @description MD5, length:32
         */
        @JvmStatic
        fun getMD5(str: String): String? {
            val digester: MessageDigest = try {
                MessageDigest.getInstance("MD5")
            } catch (e: NoSuchAlgorithmException) {
                return str
            }
            digester.update(str.toByteArray())
            val hash = digester.digest()
            val hexString = StringBuilder()
            for (aHash in hash) {
                if (0xff and aHash.toInt() < 0x10) {
                    hexString.append("0").append(Integer.toHexString(0xFF and aHash.toInt()))
                } else {
                    hexString.append(Integer.toHexString(0xFF and aHash.toInt()))
                }
            }
            return hexString.toString()
        }

        /**
         * @Author Weiguang Chen(chen2621978@gmail.com) on 2017/7/15 17:21
         * @description SHA-256, length:64
         */
        @JvmStatic
        fun getSHA256(base: String): String? {
            return try {
                val digest = MessageDigest.getInstance("SHA-256")
                val hash = digest.digest(base.toByteArray(charset("UTF-8")))
                val hexString = StringBuffer()
                for (i in hash.indices) {
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }
                hexString.toString()
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        }

    }
}