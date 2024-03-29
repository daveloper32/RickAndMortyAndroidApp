package com.daveloper.rickandmortyapp.feature_location.data.repository.external.exceptions

sealed class LocationRepositoryException(
    message: String
): Exception(
    message
) {
    class InvalidInputData(
        message: String
    ): LocationRepositoryException(
        message
    )

    class NotFoundData(
        message: String
    ): LocationRepositoryException(
        message
    )

    class NoInternetConnection(
    ): LocationRepositoryException(
        "Currently do not found any internet connection to make action"
    )

    class Unknown(
        message: String
    ): LocationRepositoryException(
        message
    )
}