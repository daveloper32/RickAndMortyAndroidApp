package com.daveloper.rickandmortyapp.feature_episode.utils.extraction

object EpisodeExtractionUtils {
    /** Regular expression in a [Regex] object that describes and allows to get the season value
     * from the base episode [String] obtained by the api.
     *
     * Ex. -> S01E01. The season value in this case is 1
     * */
    private val SEASON_REGULAR_EXP: Regex = Regex("S(\\d+)E\\d+")
    /** Regular expression in a [Regex] object that describes and allows to get the episode value
     * from the base episode [String] obtained by the api.
     *
     * Ex. -> S01E01. The episode value in this case is 1
     * */
    private val EPISODE_REGULAR_EXP: Regex = Regex("S\\d+E(\\d+)")

    /** Extraction function to try to get the Season value from a input [String].
     *
     * @param [String]
     * @return [Int]?
     * */
    fun String.extractAndGetSeason(): Int? {
        return try {
            val matchResult: MatchResult? = SEASON_REGULAR_EXP.find(this)
            val seasonStringValue: String? = matchResult?.groupValues?.get(1)
            seasonStringValue?.toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }

    /** Extraction function to try to get the Episode value from a input [String].
     *
     * @param [String]
     * @return [Int]?
     * */
    fun String.extractAndGetEpisode(): Int? {
        return try {
            val matchResult: MatchResult? = EPISODE_REGULAR_EXP.find(this)
            val episodeStringValue: String? = matchResult?.groupValues?.get(1)
            episodeStringValue?.toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }
}