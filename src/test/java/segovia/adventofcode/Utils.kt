package segovia.adventofcode

private val whitespaceRegex = Regex("\\s+")

fun String.toListOfLong() = this.split(whitespaceRegex).map { it.toLong() }