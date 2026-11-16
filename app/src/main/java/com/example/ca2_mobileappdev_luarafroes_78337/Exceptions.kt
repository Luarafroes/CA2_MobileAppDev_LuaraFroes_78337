
package com.example.ca2_mobileappdev_luarafroes_78337

// Custom exception used when a word has more than 10 characters
open class WordTooLongException(word: String) :
    Exception("The word $word is longer than 10 characters") {

    // Returns the exception name and its error message
    override fun toString(): String {
        return "WordTooLongException: $message"
    }
}

// Checks if the word has more than 10 characters
fun checkWord(word: String) {
    if (word.length > 10) {
        // Throw the custom exception if the word is too long
        throw WordTooLongException(word)
    }

    // Print a message if the word is valid
    println("$word is ok")
}

// Function to check if the light is on
fun isLightOn(): Boolean {
    // The light is currently turned off
    return false
}