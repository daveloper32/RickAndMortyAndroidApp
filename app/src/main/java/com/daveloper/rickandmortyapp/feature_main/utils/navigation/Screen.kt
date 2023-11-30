package com.daveloper.rickandmortyapp.feature_main.utils.navigation

sealed class Screen(
    val route: String,
    val index: Int
) {
    object HomeScreen: Screen("home_screen", 0)
    object CharactersScreen: Screen("characters_screen", 1)
    object EpisodesScreen: Screen("episodes_screen", 2)
    object LocationsScreen: Screen("locations_screen", 3)
}
