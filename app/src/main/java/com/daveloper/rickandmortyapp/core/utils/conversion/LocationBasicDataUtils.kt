package com.daveloper.rickandmortyapp.core.utils.conversion

import com.daveloper.rickandmortyapp.core.data.db.model.LocationBasicEntity
import com.daveloper.rickandmortyapp.core.data.network.response.LocationBasicModel
import com.daveloper.rickandmortyapp.core.data.repository.model.LocationBasicData
import com.daveloper.rickandmortyapp.core.utils.string.StringUtils.getIdAfterLastSlash

object LocationBasicDataUtils {
    /** Extension function to convert a [LocationBasicModel] data model to a [LocationBasicData] data model
     * */
    fun LocationBasicModel.toLocationBasicData(): LocationBasicData? {
        return try {
            if (this.name.isNullOrEmpty()) {
                return null
            }
            val id: Int = this.url?.getIdAfterLastSlash() ?: return null
            LocationBasicData(
                name = this.name,
                id = id,
            )
        } catch (e: Exception) {
            null
        }
    }

    /** Extension function to convert a [LocationBasicEntity] data model to a [LocationBasicData] data model
     * */
    fun LocationBasicEntity.toLocationBasicData(): LocationBasicData? {
        return try {
            LocationBasicData(
                name = this.name,
                id = this.id,
            )
        } catch (e: Exception) {
            null
        }
    }

    /** Extension function to convert a [LocationBasicData] data model to a [LocationBasicEntity] data model
     * */
    fun LocationBasicData.toLocationBasicEntity(): LocationBasicEntity? {
        return try {
            LocationBasicEntity(
                name = this.name,
                id = this.id,
            )
        } catch (e: Exception) {
            null
        }
    }
}