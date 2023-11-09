package com.daveloper.rickandmortyapp.feature_main.utils.navigation

sealed class Screen(
    val route: String
) {
    object CharactersScreen: Screen("characters_screen")
    object EpisodesScreen: Screen("episodes_screen")
    object LocationsScreen: Screen("locations_screen")
}
