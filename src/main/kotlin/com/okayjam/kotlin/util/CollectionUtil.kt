package com.okayjam.kotlin.util

import org.apache.commons.collections4.CollectionUtils
import java.util.*
import kotlin.math.min

class CollectionUtil {

    companion object{
         const val PAGE_SIZE = 2000

        @JvmStatic
        fun <T> subList(list: List<T>, pageSize1: Int = PAGE_SIZE): List<List<T>>? {
            var pageSize: Int = pageSize1
            if (CollectionUtils.isEmpty(list) ) {
                return null
            }
            if (pageSize <= 0) {
                pageSize = PAGE_SIZE
            }
            val resultList: MutableList<List<T>> =
                ArrayList()
            var subList: List<T>
            val totalCount = list.size
            //分多少次处理
            val requestCount = totalCount / pageSize
            var toIndex = 0
            for (i in 0 until requestCount) {
                val fromIndex = i * pageSize
                //如果总数少于PAGE_SIZE,为了防止数组越界,toIndex直接使用totalCount即可
                toIndex = min(totalCount, (i + 1) * pageSize)
                subList = list.subList(fromIndex, toIndex)
                resultList.add(subList)
                //总数不到一页或者刚好等于一页的时候,只需要处理一次就可以退出for循环了
            }
            if (toIndex < totalCount) {
                resultList.add(list.subList(toIndex, totalCount))
            }
            return resultList
        }
    }

}