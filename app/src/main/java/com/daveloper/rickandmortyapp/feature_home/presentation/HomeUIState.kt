package com.daveloper.rickandmortyapp.feature_home.presentation

import com.daveloper.rickandmortyapp.feature_main.utils.navigation.Screen

sealed interface HomeUIState {
    data class NavigateTo(
        val screen: Screen? = null
    ): HomeUIState
}
