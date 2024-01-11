package com.daveloper.rickandmortyapp.core.ui.components.states

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR

data class TextFieldState(
    val text: String = EMPTY_STR,
    val hint: String = EMPTY_STR,
    val isHintVisible: Boolean = true,
)
