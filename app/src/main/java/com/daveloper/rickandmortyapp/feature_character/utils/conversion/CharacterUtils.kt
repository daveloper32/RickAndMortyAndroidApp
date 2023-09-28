package com.daveloper.rickandmortyapp.feature_character.utils.conversion

import com.daveloper.rickandmortyapp.core.data.repository.model.LocationBasicData
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.conversion.LocationBasicDataUtils.toLocationBasicData
import com.daveloper.rickandmortyapp.core.utils.string.StringUtils.getIdAfterLastSlash
import com.daveloper.rickandmortyapp.feature_character.data.network.model.response.CharacterModel
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData

object CharacterUtils {
    /** Extension function to convert a [CharacterModel] data model to a [CharacterData] data model
     * @param [CharacterModel]
     * */
    fun CharacterModel.toCharacterData(): CharacterData? {
        return try {
            if (
                this.id == null
            ) {
                return null
            }
            CharacterData(
                id = this.id,
                name = this.name ?: EMPTY_STR,
                status = this.status ?: EMPTY_STR,
                species = this.species ?: EMPTY_STR,
                type = this.type ?: EMPTY_STR,
                gender = this.gender ?: EMPTY_STR,
                origin = this.origin?.toLocationBasicData() ?: LocationBasicData(),
                location = this.location?.toLocationBasicData() ?: LocationBasicData(),
                image = this.image ?: EMPTY_STR,
                episodeIds = this.episode?.mapNotNull { it.getIdAfterLastSlash() } ?: emptyList(),
                url = this.url ?: EMPTY_STR,
                created = this.created ?: EMPTY_STR,
            )
        } catch (e: Exception) {
            null
        }
    }
}