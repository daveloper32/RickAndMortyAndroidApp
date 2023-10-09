package com.daveloper.rickandmortyapp.feature_location.utils.conversion

import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.string.StringUtils.getIdAfterLastSlash
import com.daveloper.rickandmortyapp.feature_episode.utils.conversion.EpisodeUtils.toEpisodeData
import com.daveloper.rickandmortyapp.feature_location.data.db.model.LocationEntity
import com.daveloper.rickandmortyapp.feature_location.data.network.model.response.LocationModel
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.model.LocationData

object LocationUtils {
    /** Extension function to convert a [LocationModel] data model to a [LocationData] data model
     * @param [LocationModel]
     * */
    fun LocationModel.toLocationData(): LocationData? {
        return try {
            if (
                this.id == null
            ) {
                return null
            }
            LocationData(
                id = this.id,
                name = this.name ?: EMPTY_STR,
                type = this.type ?: EMPTY_STR,
                dimension = this.dimension ?: EMPTY_STR,
                residentIds = this.residents?.mapNotNull { it.getIdAfterLastSlash() } ?: emptyList(),
                url = this.url ?: EMPTY_STR,
                created = this.created ?: EMPTY_STR,
            )
        } catch (e: Exception) {
            null
        }
    }

    /** Extension function to convert a [LocationEntity] data model to a [LocationData] data model
     * @param [LocationEntity]
     * */
    fun LocationEntity.toLocationData(): LocationData? {
        return try {
            if (
                this.id == Constants.INVALID_INT
            ) {
                return null
            }
            LocationData(
                id = this.id,
                name = this.name,
                type = this.type,
                dimension = this.dimension,
                residentIds = this.residentIds,
                url = this.url,
                created = this.created,
            )
        } catch (e: Exception) {
            null
        }
    }

    /** Extension function to convert a [LocationData] data model to a [LocationEntity] data model
     * @param [LocationData]
     * */
    fun LocationData.toLocationEntity(): LocationEntity? {
        return try {
            if (
                this.id == Constants.INVALID_INT
            ) {
                return null
            }
            LocationEntity(
                id = this.id,
                name = this.name,
                type = this.type,
                dimension = this.dimension,
                residentIds = this.residentIds,
                url = this.url,
                created = this.created,
            )
        } catch (e: Exception) {
            null
        }
    }
}