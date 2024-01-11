package com.daveloper.rickandmortyapp.feature_home.presentation

import com.daveloper.rickandmortyapp.feature_home.domain.enums.AppDataType

/** The [HomeEvent] describe all the possible events that could be launched by
 * [HomeScreen].
 * */
sealed class HomeEvent {
    object Refresh: HomeEvent()

    data class LoadMoreData(
        val dataType: AppDataType
    ): HomeEvent()

    data class ScrollPosition(
        val newPosition: Int
    ): HomeEvent()

    object NavigationCompleted: HomeEvent()
}
