package com.daveloper.rickandmortyapp.feature_main.presentation.components

import com.daveloper.rickandmortyapp.core.ui.navigation.Screen

/** The [MainNavigationEvent] describe all the possible events that could be launched by
 * [MainNavigationCmp].
 * */
sealed class MainNavigationEvent {
    data class ScrollPosition(
        val newPosition: Int
    ): MainNavigationEvent()

    data class Navigation(
        val screen: Screen
    ): MainNavigationEvent()
}