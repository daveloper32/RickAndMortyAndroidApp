package com.daveloper.rickandmortyapp.feature_episode.utils.conversion

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.INVALID_INT
import com.daveloper.rickandmortyapp.core.utils.string.StringUtils.getIdAfterLastSlash
import com.daveloper.rickandmortyapp.feature_episode.data.db.model.EpisodeEntity
import com.daveloper.rickandmortyapp.feature_episode.data.network.model.response.EpisodeModel
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData
import com.daveloper.rickandmortyapp.feature_episode.utils.extraction.EpisodeExtractionUtils.extractAndGetEpisode
import com.daveloper.rickandmortyapp.feature_episode.utils.extraction.EpisodeExtractionUtils.extractAndGetSeason

object EpisodeUtils {
    /** Extension function to convert a [EpisodeModel] data model to a [EpisodeData] data model
     * @param [EpisodeModel]
     * */
    fun EpisodeModel.toEpisodeData(): EpisodeData? {
        return try {
            if (
                this.id == null
            ) {
                return null
            }
            EpisodeData(
                id = this.id,
                name = this.name ?: EMPTY_STR,
                airDate = this.airDate ?: EMPTY_STR,
                seasonNumber = this.episode?.extractAndGetSeason() ?: INVALID_INT,
                episodeNumber = this.episode?.extractAndGetEpisode() ?: INVALID_INT,
                episodeStamp = this.episode ?: EMPTY_STR,
                characterIds = this.characters?.mapNotNull { it.getIdAfterLastSlash() } ?: emptyList(),
                url = this.url ?: EMPTY_STR,
                created = this.created ?: EMPTY_STR,
            )
        } catch (e: Exception) {
            null
        }
    }

    /** Extension function to convert a [EpisodeEntity] data model to a [EpisodeData] data model
     * @param [EpisodeEntity]
     * */
    fun EpisodeEntity.toEpisodeData(): EpisodeData? {
        return try {
            if (
                this.id == INVALID_INT
            ) {
                return null
            }
            EpisodeData(
                id = this.id,
                name = this.name,
                airDate = this.airDate,
                seasonNumber = this.seasonNumber,
                episodeNumber = this.episodeNumber,
                episodeStamp = this.episodeStamp,
                characterIds = this.characterIds,
                url = this.url,
                created = this.created,
            )
        } catch (e: Exception) {
            null
        }
    }

    /** Extension function to convert a [EpisodeData] data model to a [EpisodeEntity] data model
     * @param [EpisodeData]
     * */
    fun EpisodeData.toEpisodeEntity(): EpisodeEntity? {
        return try {
            if (
                this.id == INVALID_INT
            ) {
                return null
            }
            EpisodeEntity(
                id = this.id,
                name = this.name,
                airDate = this.airDate,
                seasonNumber = this.seasonNumber,
                episodeNumber = this.episodeNumber,
                episodeStamp = this.episodeStamp,
                characterIds = this.characterIds,
                url = this.url,
                created = this.created,
            )
        } catch (e: Exception) {
            null
        }
    }
}