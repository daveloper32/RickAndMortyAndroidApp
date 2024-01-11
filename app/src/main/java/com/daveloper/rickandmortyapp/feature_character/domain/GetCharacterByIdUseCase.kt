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

/** The [GetCharacterByIdUseCase] search and try to find a [Character] based on a input id [Int].
 * */
class GetCharacterByIdUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val characterRepository: CharacterRepository
) {
    /** The [GetCharacterByIdUseCase] search and try to find a [Character] based on a input id [Int].
     *
     * @param id ([Int] type) - character id to try to search.
     * @return [UseCaseResult]<[Character]>
     * */
    suspend operator fun invoke (
        id: Int
    ): UseCaseResult<Character> {
        return try {
            val result = withContext(Dispatchers.IO) {
                characterRepository.searchCharactersById(
                    listOf(
                        id
                    )
                )
            }
            when (result) {
                is RepositoryResult.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        return UseCaseResult.Message(
                            resourceProvider.getStringResource(R.string.lab_not_found_data_error),
                            MessageResultType.ERROR
                        )
                    }
                    val data: Character? = result.data.firstOrNull()?.toDomain()
                    if (data == null) {
                        return UseCaseResult.Message(
                            resourceProvider.getStringResource(R.string.lab_not_found_data_error),
                            MessageResultType.ERROR
                        )
                    }
                    UseCaseResult.Success(
                        data
                    )
                }
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