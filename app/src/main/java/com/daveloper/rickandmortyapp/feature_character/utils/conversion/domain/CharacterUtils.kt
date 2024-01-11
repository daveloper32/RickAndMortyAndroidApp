package com.daveloper.rickandmortyapp.feature_character.utils.conversion.domain

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.INVALID_INT
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.model.CharacterData
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character

object CharacterUtils {
    /** Extension function to convert a [CharacterData] data model to a [Character] domain data
     * model.
     *
     * @param [CharacterData]
     * */
    fun CharacterData.toDomain(): Character? {
        return try {
            if (
                this.id == INVALID_INT
            ) {
                return null
            }
            Character(
                id = this.id,
                name = this.name,
                lifeStatus = this.status,
                species = this.species,
                type = this.type,
                gender = this.gender,
                origin = this.origin.name,
                currentLocation = this.location.name,
                imageUrl = this.image,
                relatedEpisodeIds = this.episodeIds,
            )
        } catch (e: Exception) {
            null
        }
    }
}