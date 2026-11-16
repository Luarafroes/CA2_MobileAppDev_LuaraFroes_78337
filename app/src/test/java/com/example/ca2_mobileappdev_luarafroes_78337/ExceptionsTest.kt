package com.example.ca2_mobileappdev_luarafroes_78337

import org.junit.Assert.*
import org.junit.Test

class ExceptionsTest {

    // Test if the check() function throws an error when the light is off
    @Test
    fun testCheckLight() {
        try {
            check(isLightOn())
            fail("Expected IllegalStateException")
        } catch (e: IllegalStateException) {
            // Check if the error message is correct
            assertEquals("The light is not turned on", e.message)
        }
    }

    // Test if require() does not accept a negative number
    @Test
    fun testRequireNumber() {
        try {
            require(-5 >= 0)
            fail("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // Check if the correct error message appears
            assertEquals("The number must not be negative", e.message)
        }
    }

    // Test if a custom exception is thrown when the word is too long
    @Test
    fun testWordTooLong() {
        try {
            checkWord("luara")
            fail("Expected WordTooLongException")
        } catch (e: WordTooLongException) {
            // Check if the error message contains the word used in the test
            assertTrue(e.message!!.contains("luara"))
        }
    }

    // Test if a normal word is accepted without throwing an exception
    @Test
    fun testWordIsValid() {
        checkWord("blablabla")
    }
}