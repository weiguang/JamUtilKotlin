package com.okayjam.kotlin.util


import org.junit.Assert
import org.junit.Test

class CollectionUtilTest {

    @Test
    fun t () {
        val list = listOf<Int>(1,2,3,4,5,6)
        val subList = CollectionUtil.subList(list, 2)
        Assert.assertEquals(3, subList?.size)
        println(subList)
    }

}
