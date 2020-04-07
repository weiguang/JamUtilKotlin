package com.okayjam.kotlin.util


import org.junit.Test

class CollectionUtilTest {

    @Test
    fun t () {
        val list = emptyList<Int>()
        val subList = CollectionUtil.subList(list, 2)
        println(subList)
    }

}
