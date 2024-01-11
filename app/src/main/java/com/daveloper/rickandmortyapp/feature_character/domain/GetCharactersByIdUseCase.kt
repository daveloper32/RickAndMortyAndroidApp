package com.daveloper.rickandmortyapp.feature_character.domain

import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.domain.CharacterUtils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

/** The [GetCharactersByIdUseCase] search and try to find a [Character] based on a input ids [Int]
 * on real time.
 * */
class GetCharactersByIdUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val characterRepository: CharacterRepository,
) {
    /** The [GetCharactersByIdUseCase] search and try to find a [Character] based on a input ids [Int]
     * on real time.
     *
     * @param ids ([List]<[Int]> type) - characters ids to try to search.
     * @return [Flow]<[List]<[Character]>>
     * */
    operator fun invoke (
        ids: List<Int> = emptyList()
    ): Flow<List<Character>> {
        return try {
            characterRepository.searchEpisodesByIdInRealTime(
                ids
            ).mapNotNull { characters ->
                characters.mapNotNull { character ->
                    character.toDomain()
                }
            }
        } catch (e: Exception) {
            flow<List<Character>> { emptyList<Character>() }
        }
    }
}