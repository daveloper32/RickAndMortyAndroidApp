package com.daveloper.rickandmortyapp.feature_episode.presentation.episode_details

/** The [EpisodeDetailsEvent] describe all the possible events that could be launched by
 * [EpisodeDetailsScreen].
 * */
sealed class EpisodeDetailsEvent {
    object OnBack: EpisodeDetailsEvent()
}
