
package com.example.ca2_mobileappdev_luarafroes_78337

open class WordTooLongException(word: String) :
    Exception("The word $word is longer than 10 characters") {

    override fun toString(): String {
        return "WordTooLongException: $message"
    }
}

fun checkWord(word: String) {
    if (word.length > 10) {
        throw WordTooLongException(word)
    }

    println("$word is ok")
}

fun isLightOn(): Boolean {
    return false
}
