// ----- Exceptions 2: creating my own exception -----

// Using open means other classes can also inherit from this exception
open class WordTooLongException(word: String) :
    Exception("The word $word is longer than 10 characters") {

    // Changing the toString message so it shows the name of my custom exception
    override fun toString(): String {
        return "WordTooLongException: $message"
    }
}

// This checks how long the word is and throws an exception if it has more than 10 letters
fun checkWord(word: String) {
    if (word.length > 10) {
        throw WordTooLongException(word)
    }
    println("$word is ok")
}

// ----- Exceptions 1: using check() and require() -----

fun isLightOn(): Boolean {
    return false   // Can change this to true to test it working
}

fun main() {

    // Using check() to see if the light is on
    // If it is false, an IllegalStateException will happen
    try {
        check(isLightOn()) { "The light is not turned on" }
    } catch (e: IllegalStateException) {
        println("Caught check problem: ${e.message}")
    }

    // Using require() to make sure the number is not negative
    // If the condition is false, it throws an IllegalArgumentException
    val number = -5   // Can change this to 5 to test it working
    try {
        require(number >= 0) { "The number must not be negative" }
    } catch (e: IllegalArgumentException) {
        println("Caught require problem: ${e.message}")
    }

    // Testing the custom exception with a word that is longer than 10 letters
    try {
        checkWord("extraordinarily")
    } catch (e: WordTooLongException) {
        println("Caught custom exception: $e")
    }
}