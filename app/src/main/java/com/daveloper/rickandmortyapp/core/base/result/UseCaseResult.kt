package com.daveloper.rickandmortyapp.core.base.result

import com.daveloper.rickandmortyapp.core.base.result.enums.MessageResultType
import com.daveloper.rickandmortyapp.core.utils.constants.Constants

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T?): UseCaseResult<T>()
    class Message(
        val message: String = Constants.EMPTY_STR,
        val messageResultType: MessageResultType = MessageResultType.SUCCESS
    ): UseCaseResult<Nothing>()
}
