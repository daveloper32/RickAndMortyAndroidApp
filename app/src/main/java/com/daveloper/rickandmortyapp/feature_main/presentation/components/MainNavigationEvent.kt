package com.daveloper.rickandmortyapp.feature_main.presentation.components

/** The [MainNavigationEvent] describe all the possible events that could be launched by
 * [MainNavigationCmp].
 * */
sealed class MainNavigationEvent {
    data class ScrollPosition(
        val newPosition: Int
    ): MainNavigationEvent()
}