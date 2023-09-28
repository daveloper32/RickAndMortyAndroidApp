package com.daveloper.rickandmortyapp.feature_episode.utils.conversion

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.string.StringUtils.getIdAfterLastSlash
import com.daveloper.rickandmortyapp.feature_episode.data.network.model.response.EpisodeModel
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.model.EpisodeData

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
                episode = this.episode ?: EMPTY_STR,
                characterIds = this.characters?.mapNotNull { it.getIdAfterLastSlash() } ?: emptyList(),
                url = this.url ?: EMPTY_STR,
                created = this.created ?: EMPTY_STR,
            )
        } catch (e: Exception) {
            null
        }
    }
}