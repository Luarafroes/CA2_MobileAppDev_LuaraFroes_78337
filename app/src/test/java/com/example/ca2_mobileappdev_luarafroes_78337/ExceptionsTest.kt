
package com.example.ca2_mobileappdev_luarafroes_78337

import org.junit.Assert.*
import org.junit.Test

class ExceptionsTest {

    @Test
    fun testCheckLight() {
        try {
            check(isLightOn())
            fail("Expected IllegalStateException")
        } catch (e: IllegalStateException) {
            assertEquals("The light is not turned on", e.message)
        }
    }

    @Test
    fun testRequireNumber() {
        try {
            require(-5 >= 0)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            assertEquals("The number must not be negative", e.message)
        }
    }

    @Test
    fun testWordTooLong() {
        try {
            checkWord("extraordinarily")
            fail("Expected WordTooLongException")
        } catch (e: WordTooLongException) {
            assertTrue(e.message!!.contains("extraordinarily"))
        }
    }

    @Test
    fun testWordIsValid() {
        checkWord("computer")
    }
}
