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

    class NoInternetConnection(
    ): CharacterRepositoryException(
        "Currently do not found any internet connection to make action"
    )

    class Unknown(
        message: String
    ): CharacterRepositoryException(
        message
    )
}

