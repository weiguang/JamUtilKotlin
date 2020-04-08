package com.okayjam.kotlin.util

import junit.framework.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class EncryptionUtilTest {

    @Before
    fun setUp() {
    }

    @Test
    fun getMD5() {
        val mD5 = EncryptionUtil.getMD5("Jam")
        println(mD5)
        assertEquals("9155e3bad8e607ea48fd6f338f076a55", mD5)
    }

    @Test
    fun getSHA256() {
        val sha = EncryptionUtil.getSHA256("Jam")
        println(sha)
        assertEquals("69023ec476beb208bd424e78682cca3f5165eb6696c21160adca8df4990cd2fd", sha)
    }

}