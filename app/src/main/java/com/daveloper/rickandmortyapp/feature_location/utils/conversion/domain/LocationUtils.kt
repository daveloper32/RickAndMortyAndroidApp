package com.daveloper.rickandmortyapp.feature_location.utils.conversion.domain

import com.daveloper.rickandmortyapp.core.utils.constants.Constants
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.model.LocationData
import com.daveloper.rickandmortyapp.feature_location.domain.model.Location

object LocationUtils {
    /** Extension function to convert a [LocationData] data model to a [Location] data model
     * @param [LocationData]
     * */
    fun LocationData.toDomain(): Location? {
        return try {
            if (
                this.id == Constants.INVALID_INT
            ) {
                return null
            }
            Location(
                id = this.id,
                name = this.name,
                type = this.type,
                dimension = this.dimension,
                residentIds = this.residentIds,
            )
        } catch (e: Exception) {
            null
        }
    }
}