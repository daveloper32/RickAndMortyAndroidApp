package com.daveloper.rickandmortyapp.feature_episode.data.repository.external.exceptions

sealed class EpisodeRepositoryException(
    message: String
): Exception(
    message
) {
    class InvalidInputData(
        message: String
    ): EpisodeRepositoryException(
        message
    )

    class NotFoundData(
        message: String
    ): EpisodeRepositoryException(
        message
    )

    class NoInternetConnection(
    ): EpisodeRepositoryException(
        "Currently do not found any internet connection to make action"
    )

    class Unknown(
        message: String
    ): EpisodeRepositoryException(
        message
    )
}

