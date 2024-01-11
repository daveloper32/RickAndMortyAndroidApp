package com.daveloper.rickandmortyapp.core.base.result

sealed class RepositoryResult<out T : Any> {
    class Success<out T : Any>(val data: T?): RepositoryResult<T>()
    class Error(val exception: Exception) : RepositoryResult<Nothing>()
}
