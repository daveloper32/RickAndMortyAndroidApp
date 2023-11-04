package com.daveloper.rickandmortyapp.feature_character.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.domain.CharacterUtils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

/** The [GetCharactersUseCase] makes a subscription and get all the [Character] saved on real
 * time.
 * */
class GetCharactersUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val characterRepository: CharacterRepository
) {
    /** The [GetCharactersUseCase] makes a subscription and get all the [Character] saved on real
     * time.
     * @param searchQuery ([String] type) -> You can filter the characters by [Character] name. By
     * default is an empty string that gets all the [Character].
     * @param lifeStatus ([String] type) -> You can filter by some [Character] life status. By
     * default filter results by all life status. If the value is 'all' the filter is not applied.
     * @param species ([String] type) -> You can filter by some [Character] species. By default filter
     * results by all species. If the value is 'all' the filter is not applied.
     * @param gender ([String] type) -> You can filter by some [Character] gender. By default filter
     * results by all genders. If the value is 'all' the filter is not applied.
     * @return [Flow]<[List]<[Character]>>
     * */
    operator fun invoke(
        searchQuery: String = EMPTY_STR,
        lifeStatus: String = resourceProvider.getStringResource(R.string.lab_all),
        species: String = resourceProvider.getStringResource(R.string.lab_all),
        gender: String = resourceProvider.getStringResource(R.string.lab_all),
    ): Flow<List<Character>> {
        return try {
            characterRepository.getCharactersInRealTime(
                searchQuery
            ).mapNotNull { characters ->
                characters.mapNotNull { character ->
                    character.toDomain()
                }
            }.map { characters -> // Filter by life status
                if (
                    lifeStatus.lowercase() != resourceProvider.getStringResource(R.string.lab_all).lowercase() &&
                    lifeStatus != EMPTY_STR
                ) { // Some specific life status
                    characters.filter { character ->
                        character.lifeStatus.lowercase() == lifeStatus.lowercase()
                    }
                } else { // All life status
                    characters
                }
            }.map { characters -> // Filter by species
                if (
                    species.lowercase() != resourceProvider.getStringResource(R.string.lab_all).lowercase() &&
                    species != EMPTY_STR
                ) { // Some specific species
                    characters.filter { character ->
                        character.species.lowercase() == species.lowercase()
                    }
                } else { // All species
                    characters
                }
            }.map { characters -> // Filter by gender
                if (
                    gender.lowercase() != resourceProvider.getStringResource(R.string.lab_all).lowercase() &&
                    gender != EMPTY_STR
                ) { // Some specific gender
                    characters.filter { character ->
                        character.gender.lowercase() == gender.lowercase()
                    }
                } else { // All genders
                    characters
                }
            }
        } catch (e: Exception) {
            flow<List<Character>> { emptyList<Character>() }
        }
    }
}