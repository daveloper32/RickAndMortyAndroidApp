package com.daveloper.rickandmortyapp.feature_character.data.repository.external.exceptions

sealed class CharacterRepositoryException(
    message: String
): Exception(
    message
) {
    class InvalidInputData(
        message: String
    ): CharacterRepositoryException(
        message
    )

    class NotFoundData(
        message: String
    ): CharacterRepositoryException(
        message
    )
}

