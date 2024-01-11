package com.daveloper.rickandmortyapp.core.utils.numbers

object IntUtils {
    /** Extension function to convert a [List]<[Int]> into a joined sequence of [String] with all
     * the values separated by commas.
     *
     * Ex. [1, 4, 10, 57] -> "1,4,10,57"
     * @param [List]<[Int]>
     * @return [String]
     * */
    fun List<Int>.toStringJoinedWithCommas(): String = this.joinToString(separator = ",")
}