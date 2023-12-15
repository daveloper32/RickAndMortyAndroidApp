package com.daveloper.rickandmortyapp.core.ui.navigation

sealed class Screen(
    val route: String,
    val index: Int = -1
) {
    object HomeScreen: Screen("home_screen", 0)
    object CharactersScreen: Screen("characters_screen", 1)
    object EpisodesScreen: Screen("episodes_screen", 2)
    object LocationsScreen: Screen("locations_screen", 3)
    object CharacterDetailsScreen: Screen("character_details_screen") {
        const val CHARACTER_ID_PARAM = "character_id"
        const val DEFAULT_CHARACTER_ID_PARAM_VALUE = -1
        fun getRouteWithParams(): String = "${Screen.CharacterDetailsScreen.route}?$CHARACTER_ID_PARAM={$CHARACTER_ID_PARAM}"
        fun createRoute(
            characterId: Int
        ) = "${Screen.CharacterDetailsScreen.route}?$CHARACTER_ID_PARAM=$characterId"
    }
    object EpisodeDetailsScreen: Screen("episode_details_screen") {
        const val EPISODE_ID_PARAM = "episode_id"
        const val DEFAULT_EPISODE_ID_PARAM_VALUE = -1
        fun getRouteWithParams(): String = "${Screen.EpisodeDetailsScreen.route}?$EPISODE_ID_PARAM={$EPISODE_ID_PARAM}"
        fun createRoute(
            episodeId: Int
        ) = "${Screen.EpisodeDetailsScreen.route}?$EPISODE_ID_PARAM=$episodeId"
    }
    object LocationDetailsScreen: Screen("location_details_screen") {
        const val LOCATION_ID_PARAM = "location_id"
        const val DEFAULT_LOCATION_ID_PARAM_VALUE = -1
        fun getRouteWithParams(): String = "${Screen.LocationDetailsScreen.route}?$LOCATION_ID_PARAM={$LOCATION_ID_PARAM}"
        fun createRoute(
            locationId: Int
        ) = "${Screen.LocationDetailsScreen.route}?$LOCATION_ID_PARAM=$locationId"
    }
}
