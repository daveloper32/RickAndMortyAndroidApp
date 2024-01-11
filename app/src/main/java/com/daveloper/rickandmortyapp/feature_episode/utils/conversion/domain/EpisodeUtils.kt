package com.daveloper.rickandmortyapp.feature_episode.utils.conversion.domain

import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData
import com.daveloper.rickandmortyapp.feature_episode.domain.model.Episode

object EpisodeUtils {
    /** Extension function to convert a [EpisodeData] data model to a [Episode] domain data
     * model.
     *
     * @param [EpisodeData]
     * */
    fun EpisodeData.toDomain(): Episode? {
        return try {
            if (
                this.id == Constants.INVALID_INT
            ) {
                return null
            }
            Episode(
                id = this.id,
                name = this.name,
                airDate = this.airDate,
                seasonNumber = this.seasonNumber,
                episodeNumber = this.episodeNumber,
                episodeStamp = this.episodeStamp,
                characterIds = this.characterIds,
            )
        } catch (e: Exception) {
            null
        }
    }
}