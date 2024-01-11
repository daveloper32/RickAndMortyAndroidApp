package com.daveloper.rickandmortyapp.feature_main.presentation.components


/**The [MainNavigationState] describes all [MainNavigationViewModel] view/screen values.
 *
 * @param isScrollingUp ([Boolean] type)
 * @param isBottomNavigationBarVisible ([Boolean] type)
 * */
data class MainNavigationState(
    val isScrollingUp: Boolean = false,
    val isBottomNavigationBarVisible: Boolean = true,
)
