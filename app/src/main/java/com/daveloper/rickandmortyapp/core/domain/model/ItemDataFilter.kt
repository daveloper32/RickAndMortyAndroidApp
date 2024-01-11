package com.daveloper.rickandmortyapp.core.domain.model

import com.daveloper.rickandmortyapp.core.utils.constants.Constants.EMPTY_STR

data class ItemDataFilter(
    var label: String = EMPTY_STR,
    var isSelected: Boolean = false,
)
