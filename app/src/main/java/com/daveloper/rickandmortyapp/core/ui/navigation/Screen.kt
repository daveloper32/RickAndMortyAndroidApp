package com.daveloper.rickandmortyapp.core.ui.navigation

sealed class Screen(
    route: String
) {
    object CharactersScreen: Screen("characters_screen")
    object EpisodesScreen: Screen("episodes_screen")
    object CharacterDetailsScreen: Screen("character_details_screen")
    object EpisodeDetailsScreen: Screen("episode_details_screen")
    object LocationDetailsScreen: Screen("location_details_screen")
}
