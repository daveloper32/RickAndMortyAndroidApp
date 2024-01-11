package com.daveloper.rickandmortyapp.feature_character.domain

import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.base.result.UseCaseResult
import com.daveloper.rickandmortyapp.core.base.result.enums.MessageResultType
import com.daveloper.rickandmortyapp.core.utils.providers.ResourceProvider
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions.CharacterRepositoryException
import com.daveloper.rickandmortyapp.feature_character.domain.model.Character
import com.daveloper.rickandmortyapp.feature_character.utils.conversion.domain.CharacterUtils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** The [UpdateCharactersUseCase] updates and gets all [Character] data.
 * */
class UpdateCharactersUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val characterRepository: CharacterRepository
) {
    /** The [UpdateCharactersUseCase] updates and gets all [Character] data.
     *
     * @param forceDataRefresh ([Boolean] type]) - By default it is set up to false value. This value
     * should only set up to true if expects to refresh all data from Network.
     * @return [UseCaseResult]<[List]<[Character]>>
     * */
    suspend operator fun invoke(
        forceDataRefresh: Boolean = false
    ): UseCaseResult<List<Character>> {
        return try {
            val result = withContext(Dispatchers.IO) {
                characterRepository.getCharacters(
                    requiresRefresh = forceDataRefresh
                )
            }
            when (result) {
                is RepositoryResult.Success -> UseCaseResult.Success(
                    result.data?.mapNotNull { it.toDomain() }
                )
                is RepositoryResult.Error -> {
                    when (result.exception) {
                        is CharacterRepositoryException.InvalidInputData -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_unknown_error),
                                MessageResultType.ERROR
                            )
                        }
                        is CharacterRepositoryException.NotFoundData -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_not_found_data_error),
                                MessageResultType.ERROR
                            )
                        }
                        is CharacterRepositoryException.NoInternetConnection -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_not_internet_connection),
                                MessageResultType.ERROR
                            )
                        }
                        is CharacterRepositoryException.Unknown -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_unknown_error),
                                MessageResultType.ERROR
                            )
                        }
                        else -> {
                            UseCaseResult.Message(
                                resourceProvider.getStringResource(R.string.lab_unknown_error),
                                MessageResultType.ERROR
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            UseCaseResult.Message(
                resourceProvider.getStringResource(R.string.lab_unknown_error),
                MessageResultType.ERROR
            )
        }
    }
}