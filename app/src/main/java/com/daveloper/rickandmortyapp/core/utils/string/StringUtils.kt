package com.daveloper.rickandmortyapp.core.utils.string

object StringUtils {
    /**
     * This extension function takes a [String] and try to return a number [Int] that could be after
     * the last slash symbol that it is contained on the input string, if is not found or the
     * conversion to [Int] fails, returns a null.
     *
     * Ex: from 'https://rickandmortyapi.com/api/location/3' it will extract the 3
     * @param this ([String] type)
     * @return [Int]?
     * */
    fun String.getIdAfterLastSlash(): Int? {
        return try {
            val lastIndex = this.lastIndexOf('/')
            if (
                lastIndex == -1 ||
                lastIndex == this.length - 1
            ) {
                return null
            }
            val stringNumber: String = this.substring(lastIndex + 1)
            stringNumber.toInt()
        } catch (e: Exception) {
            null
        }
    }
    /**
     * This extension function takes a [String] and try to return a number [Int] that could be at
     * the end of the input string, if is not found or the conversion to [Int] fails, returns a null.
     *
     * Ex: from 'https://rickandmortyapi.com/api/character?page=2' it will extract the 2
     * @param this ([String] type)
     * @return [Int]?
     * */
    fun String.getIdFromUrl(): Int? {
        return try {
            val regex: Regex = Regex("page=(\\d+)$")
            val matchResult: MatchResult? = regex.find(this)
            val stringNumber: String? = matchResult?.groupValues?.get(1)
            stringNumber?.toInt()
        } catch (e: Exception) {
            null
        }
    }
}