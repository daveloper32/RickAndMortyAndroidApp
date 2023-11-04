package com.daveloper.rickandmortyapp.feature_character.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.domain.model.ItemDataFilter
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** The [GetCharacterGendersUseCase] makes a subscription and get all the [Character] Genders
 * saved on real time.
 * */
class GetCharacterGendersUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val characterRepository: CharacterRepository
) {
    /** The [GetCharacterGendersUseCase] makes a subscription and get all the [Character] Genders
     * saved on real time.
     *
     * @return [Flow]<[List]<[String]>>
     * */
    operator fun invoke(
    ): Flow<List<String>> {
        return try {
            val allValue: String = resourceProvider.getStringResource(R.string.lab_all)
            characterRepository.getCharacterGendersInRealTime(
            ).map { genders ->
                listOf(allValue) + genders
            }
        } catch (e: Exception) {
            flow<List<String>> { emptyList<String>() }
        }
    }
}