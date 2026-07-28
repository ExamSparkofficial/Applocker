package com.antigravity.applocker.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class HashUtilTest {

    private lateinit var hashUtil: HashUtil

    @Before
    fun setUp() {
        hashUtil = HashUtil()
    }

    @Test
    fun `test hash generation is consistent`() {
        val input = "1234"
        val salt = "random_salt"
        
        val hash1 = hashUtil.hashSHA256(input, salt)
        val hash2 = hashUtil.hashSHA256(input, salt)
        
        assertEquals(hash1, hash2)
    }

    @Test
    fun `test hash changes with different salt`() {
        val input = "1234"
        
        val hash1 = hashUtil.hashSHA256(input, "salt1")
        val hash2 = hashUtil.hashSHA256(input, "salt2")
        
        assertNotEquals(hash1, hash2)
    }
}
